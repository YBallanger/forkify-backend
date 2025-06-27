package com.forkify_backend.api.dto.restaurant_statistics_dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class RestaurantSpendingDto extends RestaurantStatisticsDto {
    @NotNull
    @PositiveOrZero
    BigDecimal amountSpent;
}
