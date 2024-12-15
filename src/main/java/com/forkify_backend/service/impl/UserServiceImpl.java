package com.forkify_backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.forkify_backend.api.dto.UserDto;
import com.forkify_backend.persistence.entity.User;
import com.forkify_backend.persistence.repository.UserRepository;
import com.forkify_backend.service.UserService;
import com.forkify_backend.service.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.usersToUserDtos(users);
    }
    
}
