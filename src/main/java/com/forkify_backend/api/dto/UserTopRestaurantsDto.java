package com.forkify_backend.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTopRestaurantsDto {

    private List<UserRestaurantStatisticsDto> mostVisitedRestaurants;

    private List<UserRestaurantStatisticsDto> highestSpendingRestaurants;

    private List<UserRestaurantStatisticsDto> bestRatedRestaurants;
}
