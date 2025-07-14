package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "gym_season_tickets")
public class GymSeasonTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "ticket_type")
    private String ticketType;
}
