package com.forkify_backend.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.forkify_backend.api.dto.UserDto;
import com.forkify_backend.persistence.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User userDtoToUser(UserDto userDto);

    List<User> usersDtoToUsers(List<UserDto> userDtos);

    UserDto userToUserDto(User user);

    List<UserDto> usersToUserDtos(List<User> users);

}
