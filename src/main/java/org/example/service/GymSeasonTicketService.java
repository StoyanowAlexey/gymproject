package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entities.GymPerson;
import org.example.entities.GymSeasonTicket;
import org.example.exceptions.FakeObjectException;
import org.example.repositories.GymPersonRepository;
import org.example.repositories.GymSeasonTicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class GymSeasonTicketService {
    public final GymSeasonTicketRepository gymSeasonTicketRepository;
    public final GymPersonRepository gymPersonRepository;

    public void seasonTicketMenu(int number) {
        Scanner scanner = new Scanner(System.in);
        GymSeasonTicket gymSeasonTicket = new GymSeasonTicket();
        if (number > 1) {
            while (true) {
                System.out.println("Enter season ticket type (exit for exit) ");
                String ticketType = scanner.nextLine();
                if (Objects.equals(ticketType, "exit")) break;
                if (!gymSeasonTicketRepository.existsByTicketType(ticketType) ||  Objects.equals(ticketType, "default"))
                    System.out.println(new FakeObjectException().getMessage());
                else {
                    gymSeasonTicket = gymSeasonTicketRepository.findByTicketType(ticketType);
                    break;
                }
            }
        }
        switch (number) {
            case 1 -> {
                System.out.println("Enter new season ticket type ");
                gymSeasonTicket.setTicketType(scanner.nextLine());
                gymSeasonTicketRepository.save(gymSeasonTicket);
            }
            case 2 -> {
                System.out.println("Enter new name for season ticket ");
                gymSeasonTicket.setTicketType(scanner.nextLine());
                gymSeasonTicketRepository.save(gymSeasonTicket);
            }
            case 3 -> {
                changeSeasonTicketToDefault(gymSeasonTicket.getTicketType());
                gymSeasonTicketRepository.delete(gymSeasonTicket);
                System.out.println("season ticket was successfully deleted! ");
            }
        }
    }

    public void changeSeasonTicketToDefault(String ticketType){
        List <GymPerson> gymPersonList = gymPersonRepository.findAllBySeasonTicket_TicketType(ticketType);
        if (!gymPersonList.isEmpty()){
            gymPersonList.forEach(person -> person.setSeasonTicket(gymSeasonTicketRepository.findById(2)));
            gymPersonRepository.saveAll(gymPersonList);
        }
    }

}
