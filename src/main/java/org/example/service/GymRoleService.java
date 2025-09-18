package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entities.GymRole;
import org.example.entities.GymWorker;
import org.example.exceptions.FakeObjectException;
import org.example.repositories.GymRoleRepository;
import org.example.repositories.GymWorkerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class GymRoleService {
    public final GymRoleRepository gymRoleRepository;
    public final GymWorkerRepository gymWorkerRepository;

    /*public void rolesEnterMenu(int number) {
        Scanner scanner = new Scanner(System.in);
        GymRole gymRole = new GymRole();
        if (number > 1) {
            while (true) {
                System.out.println("Enter name of role (exit to exit) ");
                String role = scanner.nextLine();
                if (Objects.equals(role, "exit")) break;
                else if (!gymRoleRepository.existsGymRoleByRole(role) || Objects.equals(role, "Worker"))
                    System.out.println(new FakeObjectException().getMessage());
                else {
                    gymRole = gymRoleRepository.findByRole(role);
                    break;
                }
            }

        }
        switch (number) {
            case 1 -> {
                System.out.println("Enter new role name ");
                String name = scanner.nextLine();
                GymRole newGymRole = new GymRole();
                newGymRole.setRole(name);
                gymRoleRepository.save(newGymRole);
            }
            case 2 -> {
                System.out.println("Enter new name for a role ");
                gymRole.setRole(scanner.nextLine());
                gymRoleRepository.save(gymRole);
                System.out.println("------ Role was changed ------");
            }
            case 3 -> {
                changeRoleToDefault(gymRole.getRole());
                gymRoleRepository.delete(gymRole);
                System.out.println("------ Role was deleted ------");
            }
        }
    }

    public void changeRoleToDefault(String role){
        List<GymWorker> gymWorkerList = gymWorkerRepository.findAllByRole_Role(role);
        if (!gymWorkerList.isEmpty()){
            gymWorkerList.forEach(worker -> worker.setRole(gymRoleRepository.findById(102)));
            gymWorkerRepository.saveAll(gymWorkerList);
        }
    }*/

}
