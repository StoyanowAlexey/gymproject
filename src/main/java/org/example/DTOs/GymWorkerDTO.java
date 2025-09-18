package org.example.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.entities.GymRole;
import org.example.entities.enums.Gender;

@Data
@AllArgsConstructor
public class GymWorkerDTO {
    private int id;
    private String username;
    private int age;
    private String password;
    private GymRole gymRole;

}
