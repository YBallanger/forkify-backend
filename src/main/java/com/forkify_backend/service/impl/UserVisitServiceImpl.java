package com.forkify_backend.service.impl;

import com.forkify_backend.api.dto.UserVisitDto;
import com.forkify_backend.persistence.entity.Restaurant;
import com.forkify_backend.persistence.entity.User;
import com.forkify_backend.persistence.entity.UserVisit;
import com.forkify_backend.persistence.repository.RestaurantRepository;
import com.forkify_backend.persistence.repository.UserRepository;
import com.forkify_backend.persistence.repository.UserVisitRepository;
import com.forkify_backend.service.UserVisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserVisitServiceImpl implements UserVisitService {

    private final UserVisitRepository userVisitRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public UserVisit createUserVisit(String userId, UserVisitDto userVisitDto) {
        Optional<Restaurant> restauranOptional = restaurantRepository.findByName(
                userVisitDto.getRestaurantName());
        Optional<User> userOptional = userRepository.findById(userId);

        Restaurant restaurant = restauranOptional.orElseGet(() -> {
            Restaurant newRestaurant = Restaurant.builder()
                    .name(userVisitDto.getRestaurantName())
                    .build();
            return restaurantRepository.save(newRestaurant);
        });
        User user = userOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        UserVisit userVisit = UserVisit.builder()
                .user(user)
                .restaurant(restaurant)
                .amountSpent(userVisitDto.getAmountSpent())
                .rating(userVisitDto.getRating())
                .visitDate(LocalDateTime.now())
                .build();

        userVisitRepository.save(userVisit);
        return (userVisit);
    }
}
