package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.example.entities.enums.Gender;

@Entity
@Data
@Table(name = "workers")
public class GymWorker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private int age;
    @Column(name = "phone_number")
    private String phoneNubmer;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private GymRole role;
    private int salary;
}
