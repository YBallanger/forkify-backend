package com.forkify_backend.api.dto;

import com.forkify_backend.api.dto.restaurant_statistics_dtos.RestaurantStatisticsDto;
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

    private List<RestaurantStatisticsDto> mostVisitedRestaurants;

    private List<RestaurantStatisticsDto> highestSpendingRestaurants;

    private List<RestaurantStatisticsDto> bestRatedRestaurants;
}
