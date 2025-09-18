package org.example;

import lombok.RequiredArgsConstructor;
import org.example.entities.GymRole;
import org.example.entities.GymWorker;
import org.example.repositories.GymRoleRepository;
import org.example.repositories.GymWorkerRepository;
import org.example.service.GymPersonService;
import org.example.service.GymRoleService;
import org.example.service.GymSeasonTicketService;
import org.example.service.GymWorkerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class MyRunner implements CommandLineRunner {
    private final GymWorkerRepository workerRepository;
    private final GymRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        GymRole gymRole = new GymRole();
        gymRole.setRole("ADMIN");
        GymRole adminRole = roleRepository.findByRole("ADMIN")
                .orElseGet(() -> roleRepository.save(gymRole));

        // створюємо адміна, якщо нема
        if (workerRepository.findByUsername("admin").isEmpty()) {
            GymWorker admin = new GymWorker();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123")); // шифруємо пароль
            admin.setRole(adminRole);
            workerRepository.save(admin);
        }
    }

}
