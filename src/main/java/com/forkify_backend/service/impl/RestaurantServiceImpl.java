package com.forkify_backend.service.impl;

import com.forkify_backend.api.dto.RestaurantDto;
import com.forkify_backend.persistence.entity.Restaurant;
import com.forkify_backend.persistence.repository.RestaurantRepository;
import com.forkify_backend.service.RestaurantService;
import com.forkify_backend.service.mapper.RestaurantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    @Override
    public List<RestaurantDto> getAllRestaurants() {
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        restaurantList.forEach(restaurant -> System.out.println("id : " + restaurant.getRestaurantId()));
        restaurantMapper.restaurantsToRestaurantDtos(restaurantList).forEach((dto) -> System.out.println("id dto : " + dto.getRestaurantId()));
        return restaurantMapper.restaurantsToRestaurantDtos(restaurantList);
    }
}
