package org.example;

import lombok.RequiredArgsConstructor;
import org.example.entities.GymRole;
import org.example.entities.GymWorker;
import org.example.repositories.role_repo.GymRoleRepository;
import org.example.repositories.worker_repo.GymWorkerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyRunner implements CommandLineRunner {
    private final GymWorkerRepository workerRepository;
    private final GymRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${ADMINS_LOGIN_NAME}")
    private String loginName;
    @Value("${ADMINS_LOGIN_PASSWORD}")
    private String loginPassword;

    @Override
    public void run(String... args) {
        GymRole gymRole = new GymRole();
        gymRole.setRole("ROLE_ADMIN");
        GymRole adminRole = roleRepository.findByRole("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(gymRole));

        // створюємо адміна, якщо нема
        if (workerRepository.findByUsername(loginName).isEmpty()) {
            GymWorker admin = new GymWorker();
            admin.setUsername(loginName);
            admin.setPassword(passwordEncoder.encode(loginPassword)); // шифруємо пароль
            admin.setRole(adminRole);
            workerRepository.save(admin);
        }
    }

}
