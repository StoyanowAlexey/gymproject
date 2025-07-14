package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entities.GymPerson;
import org.example.entities.GymSeasonTicket;
import org.example.entities.enums.Gender;
import org.example.exceptions.FakeObjectException;
import org.example.exceptions.FakeTelegramException;
import org.example.repositories.GymPersonRepository;
import org.example.repositories.GymSeasonTicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class GymPersonService {
    private final GymPersonRepository gymPersonRepository;

    private final GymSeasonTicketRepository gymSeasonTicketRepository;


    public void userEnterMenu(int number) {
        Scanner scanner = new Scanner(System.in);
        GymPerson gymPerson = new GymPerson();
        if (number > 1 && !Objects.equals(number, 3)) {
            while (true) {
                System.out.println("Enter you name (exit for quit) ");
                String name = scanner.nextLine();
                if (Objects.equals(name, "exit")) break;
                if (!gymPersonRepository.existsByName(name)) System.out.println(new FakeObjectException().getMessage());
                else {
                    gymPerson = gymPersonRepository.getGymUserByName(name);
                    break;
                }
            }
        }
        switch (number) {
            case 1 -> gymPersonRepository.save(newGymPerson());
            case 2 -> {
                System.out.println("Enter what you want to change \n1) Name \n2) Age \n3) Gender \n4) Season ticket \n5) Telegram account ");
                gymPerson = personChangingMenu(scanner.nextInt(), gymPerson);
                gymPersonRepository.save(gymPerson);
            }
            case 3 -> {
                System.out.println("Enter id of season ticket ");
                for (GymSeasonTicket seasonTicket : gymSeasonTicketRepository.findAll()) {
                    System.out.println(seasonTicket.getId() + "     -    " + seasonTicket.getTicketType());
                }
                List<GymPerson> gymPersonList = gymPersonRepository.findAllBySeasonTicket(gymSeasonTicketRepository.getGymSeasonTicketById(scanner.nextInt()));
                if (gymPersonList.isEmpty()) System.out.println();
                else System.out.println(gymPersonList);
            }
            case 4 -> gymPersonRepository.delete(gymPerson);

        }
    }

    public GymPerson personChangingMenu(int number, GymPerson gymPerson) {
        Scanner scanner = new Scanner(System.in);
        switch (number) {
            case 1 -> {
                System.out.println("Please enter new name ");
                gymPerson.setName(scanner.nextLine());
            }
            case 2 -> {
                System.out.println("Please enter new age ");
                gymPerson.setAge(scanner.nextInt());
            }
            case 3 -> {
                System.out.println("Please enter new gender (1 - Male, 2 - Female, 3 - Other) ");
                switch (scanner.nextInt()) {
                    case 1 -> gymPerson.setGender(Gender.Male);
                    case 2 -> gymPerson.setGender(Gender.Female);
                    case 3 -> gymPerson.setGender(Gender.Other);
                }
            }
            case 4 -> {
                System.out.println("Please enter new type of season ticket ");
                for (GymSeasonTicket gymSeasonTicket : gymSeasonTicketRepository.findAll()) {
                    System.out.println(gymSeasonTicket.getId() + "     -     " + gymSeasonTicket.getTicketType());
                }
                gymPerson.setSeasonTicket(gymSeasonTicketRepository.getGymSeasonTicketById(scanner.nextInt()));
            }
            case 5 -> {
                System.out.println("Please enter add your new telegram account (with @) ");
                while (true) {
                    String tg_ac = scanner.nextLine();
                    if (!tg_ac.startsWith("@")) System.out.println(new FakeTelegramException().getMessage());
                    else {
                        gymPerson.setTelegramAccount(tg_ac);
                        break;
                    }
                }
            }
        }
        return gymPerson;
    }

    public GymPerson newGymPerson() {
        GymPerson gymPerson = new GymPerson();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name ");
        gymPerson.setName(scanner.nextLine());
        System.out.println("Enter your age ");
        gymPerson.setAge(scanner.nextInt());
        System.out.println("Enter your gender (1 - Male, 2 - Female, 3 - Other) ");
        switch (scanner.nextInt()) {
            case 1 -> gymPerson.setGender(Gender.Male);
            case 2 -> gymPerson.setGender(Gender.Female);
            case 3 -> gymPerson.setGender(Gender.Other);
        }
        System.out.println("Enter you telegram account (start with '@' ) ");
        while (true) {
            String telegram = scanner.nextLine();
            if (!telegram.startsWith("@")) System.out.println(new FakeTelegramException().getMessage());
            else {
                gymPerson.setTelegramAccount(telegram);
                break;
            }
        }
        System.out.println("Enter the season ticket ");
        for (GymSeasonTicket gymSeasonTicket : gymSeasonTicketRepository.findAll()) {
            System.out.println(gymSeasonTicket.getId() + "   -    " + gymSeasonTicket.getTicketType());
        }
        gymPerson.setSeasonTicket(gymSeasonTicketRepository.getGymSeasonTicketById(scanner.nextInt()));
        return gymPerson;
    }

}
