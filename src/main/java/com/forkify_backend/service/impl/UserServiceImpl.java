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
import com.forkify_backend.persistence.entity.User;
import com.forkify_backend.persistence.entity.UserVisit;
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
    public User createUser(UserDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
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
    public UserStatisticsDto getUserStatistics(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId);
        }

        Double amountSpent = 0.0;
        Integer numberOfVisits = 0;
        Integer numberOfNewRestaurants = 0;

        Set<Long> visitedRestaurants = new HashSet<>();

        for (UserVisit userVisit : user.get().getUserVisits()) {
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
