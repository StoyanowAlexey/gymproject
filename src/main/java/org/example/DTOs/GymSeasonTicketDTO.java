package org.example.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GymSeasonTicketDTO {
    private int id;
    private String ticketType;
}
