package com.forkify_backend.service;

import com.forkify_backend.api.dto.RestaurantDto;

import java.util.List;

public interface RestaurantService {

    /**
     * récupère tous les restaurants
     *
     * @return la liste des restaurants
     */
    List<RestaurantDto> getAllRestaurants();
}
