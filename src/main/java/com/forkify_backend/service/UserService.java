package com.forkify_backend.service;

import java.util.List;

import com.forkify_backend.api.dto.UserDto;

public interface UserService {

    public List<UserDto> getAllUsers();

}
