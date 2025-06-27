package com.forkify_backend.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRestaurantStatisticsDto {

    @NotBlank
    private String restaurantName;

    @NotNull
    @PositiveOrZero
    private BigDecimal totalAmountSpent;

    @NotNull
    @PositiveOrZero
    private Double averageRating;

    @NotNull
    @PositiveOrZero
    private Integer visitCount;
}
