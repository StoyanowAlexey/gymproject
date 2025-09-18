package org.example.requests;

import lombok.Data;
import org.example.entities.enums.Gender;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreatePersonRequest {
    int id;
    private String name;
    private int age;
    private Gender gender;
    private Integer seasonTicketId;
    private String telegramAccount;
    private String phoneNumber;
    private String gmail;
}
