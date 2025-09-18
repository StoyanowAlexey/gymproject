package org.example.mappers;

import org.example.DTOs.GymPersonDTO;
import org.example.TestDTO;
import org.example.entities.GymPerson;
import org.example.requests.CreatePersonRequest;
import org.mapstruct.*;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface GymPersonMapper {
    GymPersonDTO toDto(GymPerson gymPerson);

    @Mapping(target = "photo", ignore = true)
    GymPerson toEntity(GymPersonDTO gymPersonDTO);

    @Mapping(target = "photo", ignore = true)
    GymPerson createFromRequest(CreatePersonRequest request);

    @Mapping(target = "photo", ignore = true)
    void updateFromRequest(CreatePersonRequest request, @MappingTarget GymPerson person);

}

