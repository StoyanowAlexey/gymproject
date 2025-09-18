package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entities.GymRole;
import org.example.entities.GymWorker;
import org.example.entities.enums.Gender;
import org.example.exceptions.FakeObjectException;
import org.example.repositories.GymRoleRepository;
import org.example.repositories.GymWorkerRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class GymWorkerService {
    public GymWorkerRepository gymWorkerRepository;
    public GymRoleRepository gymRoleRepository;

    /*public void workerEnterMenu(int number) {
        Scanner scanner = new Scanner(System.in);
        GymWorker gymWorker = new GymWorker();
        if (number > 1) {
            while (true) {
                System.out.println("Enter you name (exit for exit) ");
                String name = scanner.nextLine();
                if (Objects.equals(name, "exit")) break;
                else if (!gymWorkerRepository.existsByName(name) || Objects.equals(name, "Worker"))
                    System.out.println(new FakeObjectException().getMessage());
                else {
                    gymWorker = gymWorkerRepository.findGymPersonByName(name);
                    break;
                }
            }
        }
        switch (number) {
            case 1 -> gymWorkerRepository.save(newGymWorker());
            case 2 -> {
                System.out.println("Enter what you want to change \n1)Name \n2)Age \n3)Phone Number \n4)Salary \n5)Role \n6)Gender ");
                int number_changing = scanner.nextInt();
                gymWorker = workerChangingMenu(number_changing, gymWorker);
                gymWorkerRepository.save(gymWorker);
            }
            case 3 -> gymWorkerRepository.delete(gymWorker);
        }
    }

    public GymWorker workerChangingMenu(int number, GymWorker gymWorker) {
        Scanner scanner = new Scanner(System.in);
        switch (number) {
            case 1 -> {
                System.out.println("Please enter new name");
                gymWorker.setName(scanner.nextLine());
            }
            case 2 -> {
                System.out.println("Please enter new age ");
                gymWorker.setAge(scanner.nextInt());
            }
            case 3 -> {
                System.out.println("Please enter new phone number ");
                gymWorker.setPhoneNubmer(scanner.nextLine());
            }
            case 4 -> {
                System.out.println("Please enter new salary ");
                gymWorker.setSalary(scanner.nextInt());
            }
            case 5 -> {
                System.out.println("Please enter the number of role");
                for (GymRole role : gymRoleRepository.findAll()) {
                    System.out.println("\n" + role.getRole() + " -   number  " + role.getId());
                }
                int role_id = scanner.nextInt();
                //gymRoleRepository.findById(role_id)
                gymWorker.setRole(gymRoleRepository.findById(role_id));
            }
            case 6 -> {
                System.out.println("Please enter new gender (1 - Male, 2 - Female, 3 - Other) ");
                switch (scanner.nextInt()) {
                    case 1 -> gymWorker.setGender(Gender.Male);
                    case 2 -> gymWorker.setGender(Gender.Female);
                    case 3 -> gymWorker.setGender(Gender.Other);
                }
            }
        }
        return gymWorker;
    }

    public GymWorker newGymWorker() {
        GymWorker gymWorker = new GymWorker();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name ");
        gymWorker.setName(scanner.nextLine());
        System.out.println("Enter your phone number  ");
        gymWorker.setPhoneNubmer(scanner.nextLine());
        System.out.println("Enter your age ");
        gymWorker.setAge(scanner.nextInt());
        System.out.println("Enter your salary ");
        gymWorker.setSalary(scanner.nextInt());
        System.out.println("Enter your gender (1 - Male, 2 - Female, 3 - Other) ");
        int number = scanner.nextInt();
        switch (number) {
            case 1 -> gymWorker.setGender(Gender.Male);
            case 2 -> gymWorker.setGender(Gender.Female);
            case 3 -> gymWorker.setGender(Gender.Other);
        }
        System.out.println("Enter the role ");
        for (GymRole role : gymRoleRepository.findAll()) {
            System.out.println("\n" + role.getRole() + "  -  number  " + role.getId());
        }
        number = scanner.nextInt();
        gymWorker.setRole(gymRoleRepository.findById(number));
        return gymWorker;
    }
    */
}
