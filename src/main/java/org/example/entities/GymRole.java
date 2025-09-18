package org.example.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "gym_roles")
public class GymRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String role;
}
