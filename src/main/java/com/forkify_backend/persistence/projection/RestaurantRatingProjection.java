package com.forkify_backend.persistence.projection;

public interface RestaurantRatingProjection extends RestaurantStatisticsProjection {
    Double getRating();
}
