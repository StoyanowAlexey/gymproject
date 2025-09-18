package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.entities.enums.Gender;

@Entity
@Data
@Getter
@Setter
@Table(name = "gym_workers")
public class GymWorker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private GymRole role;
}
