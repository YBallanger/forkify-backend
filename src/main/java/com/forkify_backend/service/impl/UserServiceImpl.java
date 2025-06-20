package com.forkify_backend.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.forkify_backend.api.dto.UserDto;
import com.forkify_backend.api.dto.UserStatisticsDto;
import com.forkify_backend.persistence.entity.Role;
import com.forkify_backend.persistence.entity.User;
import com.forkify_backend.persistence.entity.UserVisit;
import com.forkify_backend.persistence.repository.RoleRepository;
import com.forkify_backend.persistence.repository.UserRepository;
import com.forkify_backend.security.RoleConstants;
import com.forkify_backend.service.UserService;
import com.forkify_backend.service.mapper.UserMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public User createUser(UserDto userDto) {
        Optional<Role> roleOptional = roleRepository.findByName(RoleConstants.USER);
        if (roleOptional.isEmpty()) {
            throw new IllegalArgumentException("Role 'USER' not found in the database.");
        }

        try {
            FirebaseAuth.getInstance().getUser(userDto.getUserId());
        } catch (FirebaseAuthException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Firebase id", e);
        }

        User user = User.builder()
                .userId(userDto.getUserId())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .userVisits(new HashSet<>())
                .build();
        userRepository.save(user);
        return user;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.usersToUserDtos(users);
    }

    @Override
    public UserStatisticsDto getUserStatistics(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId));

        Double amountSpent = 0.0;
        Integer numberOfVisits = 0;
        Integer numberOfNewRestaurants = 0;

        Set<Long> visitedRestaurants = new HashSet<>();

        for (UserVisit userVisit : user.getUserVisits()) {
            amountSpent += userVisit.getAmountSpent();
            numberOfVisits++;

            Long restaurantId = userVisit.getRestaurant().getRestaurantId(); // Assumes userVisit has a Restaurant
                                                                             // object with
            if (!visitedRestaurants.contains(restaurantId)) {
                visitedRestaurants.add(restaurantId);
                numberOfNewRestaurants++;
            }
        }

        return UserStatisticsDto.builder()
                .amountSpent(amountSpent)
                .numberOfVisits(numberOfVisits)
                .numberOfNewRestaurants(numberOfNewRestaurants)
                .build();
    }
}
