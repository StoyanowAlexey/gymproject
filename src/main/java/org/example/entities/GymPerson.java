package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.entities.enums.Gender;

@Getter
@Setter
@Data
@Entity
@Table(name = "gym_visitors")
public class GymPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private int age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @ManyToOne
    @JoinColumn(name = "season_ticket_id")
    private GymSeasonTicket seasonTicket;
    @Column(name = "telegram_account")
    private String telegramAccount;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String gmail;
    private String photo;

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Age: %d, Gender: %s, Telegram: %s",
                id, name, age, gender, telegramAccount == null || telegramAccount.isEmpty() ? "Not provided" : telegramAccount);
    }
}
