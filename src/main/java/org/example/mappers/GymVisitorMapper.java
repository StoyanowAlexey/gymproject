package org.example.mappers;

import org.example.DTOs.GymPersonDTO;
import org.example.entities.GymVisitor;
import org.example.requests.CreatePersonRequest;
import org.mapstruct.*;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface GymVisitorMapper {

    @Mapping(target = "photo", ignore = true)
    GymVisitor toEntity(GymPersonDTO gymPersonDTO);

    @Mapping(target = "photo", ignore = true)
    GymVisitor createFromRequest(CreatePersonRequest request);

    @Mapping(target = "photo", ignore = true)
    void updateFromRequest(CreatePersonRequest request, @MappingTarget GymVisitor person);

}

