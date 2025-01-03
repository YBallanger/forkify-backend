package com.forkify_backend.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserStatisticsDto {
    @NotNull
    @PositiveOrZero
    Double amountSpent;

    @NotNull
    @PositiveOrZero
    Integer numberOfVisits;

    @NotNull
    @PositiveOrZero
    Integer numberOfNewRestaurants;
}
