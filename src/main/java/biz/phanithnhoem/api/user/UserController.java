package biz.phanithnhoem.api.user;

import biz.phanithnhoem.api.user.web.CreateUserDto;
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

    @GetMapping("/search")
    public ApiResponse<Object> searchUsers(@RequestParam(required = false) String fullName,
                                           @RequestParam(required = false) String username,
                                           @RequestParam(required = false) String email){
        List<UserDto> userDtoList = userService.searchUsers(fullName, username, email);
        return ApiResponse.builder()
                .status("Success")
                .code(HttpStatus.OK.value())
                .message(userDtoList.size() + " users found matching the criteria.")
                .data(userDtoList)
                .timestamp(Instant.now())
                .build();
    }

    @GetMapping
    public ApiPagedResponse<Object> findAllWithPagination(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "8") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("username"));
        List<UserDto> userDtoList = userService.findAllWithPagination(pageable);

        return ApiPagedResponse.builder()
                .status("Success")
                .message(userDtoList.isEmpty() ? "The requested page exceeds the total number of pages" : "Find all users with pagination successfully")
                .code(HttpStatus.OK.value())
                .data(userDtoList)
                .pageNo(pageable.getPageNumber())
                .recordCount(userDtoList.size())
                .totalRecords(userService.getTotalUserCount())
                .totalPages(userService.getTotalUserCount()/pageable.getPageSize() + 1)
                .timestamp(Instant.now())
                .build();
    }

    @PostMapping
    public ApiResponse<Object> createNewUser(@Valid @RequestBody CreateUserDto createUserDto){
        System.out.println(createUserDto);
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
