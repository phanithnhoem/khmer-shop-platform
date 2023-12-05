package biz.phanithnhoem.api.user;

import biz.phanithnhoem.api.user.web.CreateUserDto;
import biz.phanithnhoem.api.user.web.UpdateUserDto;
import biz.phanithnhoem.api.user.web.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public void updateByUuid(String uuid, UpdateUserDto updateUserDto) {
        // Check user exist in database or not
        if (userRepository.existsByUuid(uuid)) {
            // Load user from database
            User user = userRepository.findByUuidAndIsDeletedFalse(uuid)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            String.format("User with UUID: %s has not been found.", uuid)));

            // Map updating user partially
            if (updateUserDto != null) {
                userMapper.fromUpdateUserDto(user, updateUserDto);
                user.setUpdatedAt(Instant.now());
            }

            // Save the latest user
            userRepository.save(user);
            return;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User with UUID: %s has not been found.", uuid));
    }

    @Override
    public UserDto findByUuid(String uuid) {
        User user = userRepository.findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("User with UUID: %s has not been found.", uuid)));
        return userMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> dynamicSearchUser(String fullName, String username, String email) {
        List<User> users = userRepository.dynamicSearchUser(fullName, username, email);
        if (users.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users found matching the criteria.");
        }
        return userMapper.toUserDtoList(users);
    }

    @Transactional
    @Override
    public void createNewUser(CreateUserDto createUserDto) {

        // Check if the provided username or email in the createUserDto already exists
        if (existsByUsernameOrEmail(createUserDto.username(), createUserDto.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username or email already exists...");
        }

        // Convert the data in the createUserDto to a User entity using the userMapper
        User user = userMapper.fromCreateUserDto(createUserDto);
        user.setUuid(UUID.randomUUID().toString());
        user.setIsDeleted(false);
        user.setIsVerified(false);
        user.setCreatedAt(Instant.now());

        // Check if all role IDs provided in the createUserDto exist in the roleRepository
        if (!createUserDto.roleIds().stream().allMatch(roleRepository::existsById)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found. Please provide a valid role ID.");
        }

        // Create a set of Role entities based on the role IDs provided in createUserDto
        Set<Role> roles = createUserDto.roleIds().stream()
                .map(roleId -> Role.builder().id(roleId).build())
                .collect(Collectors.toSet());

        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public List<UserDto> findAllWithPagination(Pageable pageable) {
        List<User> users = userRepository.findAll(pageable).get().toList();
        return userMapper.toUserDtoList(users);
    }

    @Override
    public Long getTotalUserCount() {
        return userRepository.count();
    }

    // This method used to check username and email exist in database or not
    private boolean existsByUsernameOrEmail(String username, String email) {
        return userRepository.existsByUsernameAndIsDeletedFalse(username) ||
                userRepository.existsByEmailAndIsDeletedFalse(email);
    }
}
