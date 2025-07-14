package org.example.mappers;

import org.example.DTOs.GymSeasonTicketDTO;
import org.example.entities.GymSeasonTicket;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface GymSeasonTicketMapper {
    public GymSeasonTicketDTO toDto(GymSeasonTicket gymSeasonTicket);

    public GymSeasonTicket toEntity(GymSeasonTicketDTO gymSeasonTicketDTO);
}
