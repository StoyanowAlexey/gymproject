package org.example.mappers;

import org.example.DTOs.GymWorkerDTO;
import org.example.entities.GymWorker;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface GymWorkerMapper {
    public GymWorker toEntity(GymWorkerDTO gymWorkerDTO);

    public GymWorkerDTO toDTO(GymWorker gymWorker);
}
