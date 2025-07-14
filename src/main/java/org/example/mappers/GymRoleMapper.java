package org.example.mappers;


import org.example.DTOs.GymRoleDTO;
import org.example.entities.GymRole;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface GymRoleMapper {
    GymRoleDTO toDto(GymRole gymRole);

    GymRole toEntity(GymRoleDTO gymRoleDTO);
}
