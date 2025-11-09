package com.forkify_backend.api.dto.restaurant_statistics_dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public abstract class RestaurantStatisticsDto {
    @NotBlank
    protected String restaurantName;
}
