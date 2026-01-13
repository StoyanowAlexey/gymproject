package org.example.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "visitors_season_tickets", schema = "my_shema")
public class VisitorSeasonTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "gym_visitor_id")
    private GymVisitor gymVisitor;

    @ManyToOne
    @JoinColumn(name = "gym_season_ticket_id")
    private GymSeasonTicket gymSeasonTicket;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "expire_data")
    private Instant expireData;

}