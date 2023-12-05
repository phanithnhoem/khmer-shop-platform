package biz.phanithnhoem.api.user;

import biz.phanithnhoem.api.user.web.CreateUserDto;
import biz.phanithnhoem.api.user.web.UpdateUserDto;
import biz.phanithnhoem.api.user.web.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    /**
     * Creates a new user based on the provided data.
     *
     * @param createUserDto the data for creating the new user.
     */
    void createNewUser(CreateUserDto createUserDto);
    List<UserDto> findAllWithPagination(Pageable pageable);
    Long getTotalUserCount();

    List<UserDto> dynamicSearchUser(String fullName, String username, String email);

    UserDto findByUuid(String uuid);

    void updateByUuid(String uuid, UpdateUserDto updateUserDto);

}
