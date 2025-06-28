package com.forkify_backend.service.impl;

import com.forkify_backend.api.dto.*;
import com.forkify_backend.persistence.entity.Role;
import com.forkify_backend.persistence.entity.User;
import com.forkify_backend.persistence.projection.RestaurantRatingProjection;
import com.forkify_backend.persistence.projection.RestaurantSpendingProjection;
import com.forkify_backend.persistence.projection.RestaurantVisitProjection;
import com.forkify_backend.persistence.repository.UserRepository;
import com.forkify_backend.persistence.repository.UserVisitRepository;
import com.forkify_backend.service.AuthenticationService;
import com.forkify_backend.service.RoleService;
import com.forkify_backend.service.UserService;
import com.forkify_backend.service.mapper.RestaurantStatisticsMapper;
import com.forkify_backend.service.mapper.UserMapper;
import com.forkify_backend.service.mapper.UserVisitMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserVisitRepository userVisitRepository;
    private final RoleService roleService;
    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;
    private final UserVisitMapper userVisitMapper;
    private final RestaurantStatisticsMapper restaurantStatisticsMapper;

    @Override
    public User createUser(UserSignupDto userSignupDto) {
        Set<Role> roles = roleService.getDefaultRole();
        User user = new User(userSignupDto, roles);
        userRepository.save(user);
        return user;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.usersToUserDtos(users);
    }

    @Override
    public Set<UserVisitDto> getUserVisits(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId));
        return userVisitMapper.userVisitsToUserVisitDtos(user.getUserVisits());
    }

    @Override
    public UserStatisticsDto getUserStatistics(String userId) {
        Integer totalVisits = userVisitRepository.countByUser_UserId(userId);
        Integer distinctRestaurants = userVisitRepository.countDistinctRestaurantsByUserId(userId);
        BigDecimal totalAmountSpent = userVisitRepository.sumAmountSpentByUserId(userId);

        return UserStatisticsDto.builder()
                .numberOfVisits(totalVisits)
                .numberOfNewRestaurants(distinctRestaurants)
                .amountSpent(totalAmountSpent)
                .build();
    }


    @Override
    public UserTopRestaurantsDto getUserTopRestaurants(String userId) {
        List<RestaurantVisitProjection> restaurantVisitProjections = userVisitRepository.findTop3VisitedRestaurantsByUserId(userId);
        List<RestaurantRatingProjection> restaurantRatingProjections = userVisitRepository.findTop3RatedRestaurantsByUserId(userId);
        List<RestaurantSpendingProjection> restaurantSpendingProjections = userVisitRepository.findTop3SpendingRestaurantsByUserId(userId);

        return UserTopRestaurantsDto.builder()
                .mostVisitedRestaurants(new ArrayList<>(restaurantStatisticsMapper.toRestaurantVisitDtos(restaurantVisitProjections)))
                .highestSpendingRestaurants(new ArrayList<>(restaurantStatisticsMapper.toRestaurantSpendingDtos(restaurantSpendingProjections)))
                .bestRatedRestaurants(new ArrayList<>(restaurantStatisticsMapper.toRestaurantRatingDtos(restaurantRatingProjections)))
                .build();
    }
}
