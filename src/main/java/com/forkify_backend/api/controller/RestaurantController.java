package com.forkify_backend.api.controller;

import com.forkify_backend.api.dto.RestaurantDto;
import com.forkify_backend.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    /**
     * Récupère la liste de tous les restaurants.
     *
     * @return une liste de restaurants
     */
    @GetMapping("")
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants() {
        List<RestaurantDto> restaurantDtos = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurantDtos);
    }
}
