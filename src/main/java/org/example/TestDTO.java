package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entities.GymSeasonTicket;
import org.example.entities.enums.Gender;

@Data
@NoArgsConstructor
public class TestDTO {
    private int id;
    private String name;
    private int age;
    private Gender gender;
    private GymSeasonTicket seasonTicketId;
    private String telegramAccount;


}
