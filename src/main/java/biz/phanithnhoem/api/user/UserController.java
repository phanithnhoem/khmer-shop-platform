package biz.phanithnhoem.api.user;

import biz.phanithnhoem.api.user.web.CreateUserDto;
import biz.phanithnhoem.api.user.web.UpdateUserDto;
import biz.phanithnhoem.api.user.web.UserDto;
import biz.phanithnhoem.base.ApiPagedResponse;
import biz.phanithnhoem.base.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{uuid}")
    public ApiResponse<Object> updateByUuid(@PathVariable String uuid,
                                            @RequestBody UpdateUserDto updateUserDto){

        userService.updateByUuid(uuid, updateUserDto);
        return ApiResponse.builder()
                .status("Success")
                .code(HttpStatus.OK.value())
                .message("User has been update successfully.")
                .data(updateUserDto)
                .timestamp(Instant.now())
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{uuid}")
    public ApiResponse<Object> findByUuid(@PathVariable String uuid){

        UserDto userDto = userService.findByUuid(uuid);
        return ApiResponse.builder()
                .status("Success")
                .code(HttpStatus.OK.value())
                .message(String.format("User with UUID: %s has been found.", uuid))
                .data(userDto)
                .timestamp(Instant.now())
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public ApiResponse<Object> dynamicSearchUser(@RequestParam(required = false) String fullName,
                                                 @RequestParam(required = false) String username,
                                                 @RequestParam(required = false) String email){

        List<UserDto> userDtoList = userService.dynamicSearchUser(fullName, username, email);
        return ApiResponse.builder()
                .status("Success")
                .code(HttpStatus.OK.value())
                .message(userDtoList.size() + " users found matching the criteria.")
                .data(userDtoList)
                .timestamp(Instant.now())
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ApiPagedResponse<Object> findAllWithPagination(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "8") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("username"));
        List<UserDto> userDtoList = userService.findAllWithPagination(pageable);

        return ApiPagedResponse.builder()
                .status("Success")
                .message(userDtoList.isEmpty() ? "The requested page exceeds the total number of pages" : "Find all users with pagination successfully")
                .code(HttpStatus.OK.value())
                .data(userDtoList)
                .pageNo(pageable.getPageNumber())
                .recordCount(pageable.getPageSize())
                .totalRecords(userService.getTotalUserCount())
                .totalPages((long) Math.ceil(userService.getTotalUserCount()/pageable.getPageSize()))
                .timestamp(Instant.now())
                .build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<Object> createNewUser(@Valid @RequestBody CreateUserDto createUserDto){

        userService.createNewUser(createUserDto);
        return ApiResponse.builder()
                .status("Success")
                .code(HttpStatus.CREATED.value())
                .message("User has been created successfully")
                .data(createUserDto)
                .timestamp(Instant.now())
                .build();
    }
}
