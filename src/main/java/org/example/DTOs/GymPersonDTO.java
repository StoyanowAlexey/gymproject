package org.example.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.entities.GymSeasonTicket;
import org.example.entities.enums.Gender;

@Data
@AllArgsConstructor
public class GymPersonDTO {
    private int id;
    private String name;
    private int age;
    private Gender gender;
    private GymSeasonTicket seasonTicketId;
    private String telegramAccount;
    private String phoneNumber;
    private String gmail;

}
