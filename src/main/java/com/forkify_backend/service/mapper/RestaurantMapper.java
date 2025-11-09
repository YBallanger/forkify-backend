package com.forkify_backend.service.mapper;

import com.forkify_backend.api.dto.RestaurantDto;
import com.forkify_backend.persistence.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    @Mapping(source = "restaurantId", target = "restaurantId")
    RestaurantDto restaurantToRestaurantDto(Restaurant restaurant);
    
    @Mapping(source = "restaurantId", target = "restaurantId")
    Restaurant restaurantDtoToRestaurant(RestaurantDto restaurantDto);

    List<RestaurantDto> restaurantsToRestaurantDtos(List<Restaurant> restaurants);

    List<Restaurant> restaurantDtosToRestaurants(List<RestaurantDto> restaurantDtos);

}
