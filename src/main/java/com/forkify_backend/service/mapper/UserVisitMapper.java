package com.forkify_backend.service.mapper;

import com.forkify_backend.api.dto.UserVisitDto;
import com.forkify_backend.persistence.entity.UserVisit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserVisitMapper {

    UserVisit userVisitDtoToUserVisit(UserVisitDto userVisitDto);

    @Mapping(target = "restaurantName", source = "restaurant.name")
    UserVisitDto userVisitToUserVisitDto(UserVisit userVisit);

    Set<UserVisit> userVisitDtosToUserVisits(Set<UserVisitDto> userVisitDtos);

    Set<UserVisitDto> userVisitsToUserVisitDtos(Set<UserVisit> userVisits);
}
