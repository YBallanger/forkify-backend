package com.forkify_backend.api.dto.restaurant_statistics_dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class RestaurantRatingDto extends RestaurantStatisticsDto {
    @NotNull
    @PositiveOrZero
    Double rating;
}
