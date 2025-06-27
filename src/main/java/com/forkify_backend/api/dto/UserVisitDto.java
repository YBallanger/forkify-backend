package com.forkify_backend.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserVisitDto {

    @NotBlank
    private String restaurantName;

    @NotNull
    @PositiveOrZero
    private BigDecimal amountSpent;

    @NotNull
    @PositiveOrZero
    private Double rating;
}
