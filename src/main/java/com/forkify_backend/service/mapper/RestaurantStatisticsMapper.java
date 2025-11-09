package com.forkify_backend.service.mapper;

import com.forkify_backend.api.dto.restaurant_statistics_dtos.RestaurantRatingDto;
import com.forkify_backend.api.dto.restaurant_statistics_dtos.RestaurantSpendingDto;
import com.forkify_backend.api.dto.restaurant_statistics_dtos.RestaurantVisitDto;
import com.forkify_backend.persistence.projection.RestaurantRatingProjection;
import com.forkify_backend.persistence.projection.RestaurantSpendingProjection;
import com.forkify_backend.persistence.projection.RestaurantVisitProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantStatisticsMapper {
    @Mapping(source = "restaurantName", target = "restaurantName")
    @Mapping(source = "totalVisits", target = "totalVisits")
    RestaurantVisitDto toRestaurantVisitDto(RestaurantVisitProjection projection);

    @Mapping(source = "restaurantName", target = "restaurantName")
    @Mapping(source = "totalVisits", target = "totalVisits")
    List<RestaurantVisitDto> toRestaurantVisitDtos(List<RestaurantVisitProjection> projections);

    RestaurantRatingDto toRestaurantRatingDto(RestaurantRatingProjection projection);

    List<RestaurantRatingDto> toRestaurantRatingDtos(List<RestaurantRatingProjection> projections);

    @Mapping(source = "restaurantName", target = "restaurantName")
    @Mapping(source = "amountSpent", target = "amountSpent")
    RestaurantSpendingDto toRestaurantSpendingDto(RestaurantSpendingProjection projection);

    @Mapping(source = "restaurantName", target = "restaurantName")
    @Mapping(source = "amountSpent", target = "amountSpent")
    List<RestaurantSpendingDto> toRestaurantSpendingDtos(List<RestaurantSpendingProjection> projections);
}
