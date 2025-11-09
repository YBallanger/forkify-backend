package com.forkify_backend.persistence.projection;

import java.math.BigDecimal;

public interface RestaurantSpendingProjection extends RestaurantStatisticsProjection {
    BigDecimal getAmountSpent();
}
