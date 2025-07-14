package org.example.mappers;

import org.example.DTOs.GymPersonDTO;
import org.example.TestDTO;
import org.example.entities.GymPerson;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface GymPersonMapper {
    GymPersonDTO toDto(GymPerson gymPerson);

    GymPerson toEntity(GymPersonDTO gymPersonDTO);

}

