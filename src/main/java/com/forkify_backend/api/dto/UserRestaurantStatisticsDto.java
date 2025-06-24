package com.forkify_backend.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class UserRestaurantStatisticsDto {
    @NotBlank
    String restaurantName;

    @NotNull
    @PositiveOrZero
    Double amountSpent;

    @NotNull
    @PositiveOrZero
    Integer numberOfVisits;

    @NotNull
    @PositiveOrZero
    Double rating;
}
