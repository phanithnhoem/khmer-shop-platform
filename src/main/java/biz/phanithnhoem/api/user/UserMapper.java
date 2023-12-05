package biz.phanithnhoem.api.user;

import biz.phanithnhoem.api.user.web.CreateUserDto;
import biz.phanithnhoem.api.user.web.UpdateUserDto;
import biz.phanithnhoem.api.user.web.UserDto;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.lang.annotation.Target;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User fromCreateUserDto(CreateUserDto createUserDto);
    UserDto toUserDto(User user);
    User fromUserDto(UserDto userDto);
    List<UserDto> toUserDtoList(List<User> users);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUpdateUserDto(@MappingTarget User user, UpdateUserDto updateUserDto);
}
