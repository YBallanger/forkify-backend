package com.forkify_backend.service.impl;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.forkify_backend.api.dto.UserVisitDto;
import com.forkify_backend.persistence.entity.Restaurant;
import com.forkify_backend.persistence.entity.User;
import com.forkify_backend.persistence.entity.UserVisit;
import com.forkify_backend.persistence.repository.RestaurantRepository;
import com.forkify_backend.persistence.repository.UserRepository;
import com.forkify_backend.persistence.repository.UserVisitRepository;
import com.forkify_backend.service.UserVisitService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserVisitServiceImpl implements UserVisitService {

    private final UserVisitRepository userVisitRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public UserVisit createUserVisit(UserVisitDto userVisitDto) {
        Optional<Restaurant> restauranOptional = restaurantRepository.findByNameAndAddress(
                userVisitDto.getName(),
                userVisitDto.getAddress());
        Optional<User> userOptional = userRepository.findById(userVisitDto.getUserId());

        Restaurant restaurant = restauranOptional.orElseGet(() -> {
            Restaurant newRestaurant = Restaurant.builder()
                    .name(userVisitDto.getName())
                    .address(userVisitDto.getAddress())
                    .build();
            return restaurantRepository.save(newRestaurant);
        });
        User user = userOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User not found with id: " + userVisitDto.getUserId());
        }

        UserVisit userVisit = UserVisit.builder()
                .user(user)
                .restaurant(restaurant)
                .amountSpent(userVisitDto.getAmountSpent())
                .rating(userVisitDto.getAmountSpent())
                .visitDate(userVisitDto.getVisitDate())
                .build();

        userVisitRepository.save(userVisit);
        return (userVisit);
    }

}
