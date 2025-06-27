package com.forkify_backend.persistence.projection;

public interface RestaurantVisitProjection extends RestaurantStatisticsProjection {
    Long getTotalVisits();
}
