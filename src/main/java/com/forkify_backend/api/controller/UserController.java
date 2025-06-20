package com.forkify_backend.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.forkify_backend.api.dto.UserDto;
import com.forkify_backend.api.dto.UserStatisticsDto;
import com.forkify_backend.persistence.entity.User;
import com.forkify_backend.service.UserService;

import lombok.AllArgsConstructor;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto) {
        User user = userService.createUser(userDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(user.getUserId())
            .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUsers();
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/{id}/statistics")
    public ResponseEntity<UserStatisticsDto> getUserStatistics(@PathVariable String id) {
        UserStatisticsDto userStatisticsDto = userService.getUserStatistics(id);
        return ResponseEntity.ok(userStatisticsDto);
    }

}
