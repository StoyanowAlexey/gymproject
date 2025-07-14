package org.example;

import lombok.RequiredArgsConstructor;
import org.example.service.GymPersonService;
import org.example.service.GymRoleService;
import org.example.service.GymSeasonTicketService;
import org.example.service.GymWorkerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class MyRunner implements CommandLineRunner {
    private final GymPersonService gymPersonService;
    private final GymWorkerService gymWorkerService;
    private final GymRoleService gymRoleService;
    private final GymSeasonTicketService gymSeasonTicketService;

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter with which table you want to work \n1) Workers \n2) Roles \n3) Person \n4) Season ticket \n0) Exit");
            int numberTable = scanner.nextInt();
            if (numberTable == 0) break;
            else {
                allEnterMenu(numberTable);
            }

        }
    }

    public void allEnterMenu(int number) {
        Scanner scanner = new Scanner(System.in);
        switch (number) {
            case 1 -> {
                while (true) {
                    System.out.println("Enter the number \n1)Create new gym account \n2)Change data of your account \n3)Delete your account \n0) Exit");
                    int numberWorker = scanner.nextInt();
                    if (numberWorker == 0) break;
                    gymWorkerService.workerEnterMenu(numberWorker);
                }
            }
            case 2 -> {
                while (true) {
                    System.out.println("Enter the number \n1) Create new role \n2) Change role (by name) \n3) Delete the role \n0) Exit");
                    int numberRole = scanner.nextInt();
                    if (numberRole == 0) break;
                    gymRoleService.rolesEnterMenu(numberRole);
                }
            }
            case 3 -> {
                while (true) {
                    System.out.println("Enter the number \n1) Create new person \n2) Change person (by name) \n3) Show all people by season ticket \n4) Delete person  \n0) Exit");
                    int numberPerson = scanner.nextInt();
                    if (numberPerson == 0) break;
                    gymPersonService.userEnterMenu(numberPerson);
                }
            }
            case 4 -> {
                while (true) {
                    System.out.println("Enter the number \n1) Create new season ticket \n2) Change name of season ticket \n3) Delete season ticket \n0) Exit");
                    int numberSeasonTicket = scanner.nextInt();
                    if (numberSeasonTicket == 0) break;
                    gymSeasonTicketService.seasonTicketMenu(numberSeasonTicket);
                }
            }
        }
    }

}
