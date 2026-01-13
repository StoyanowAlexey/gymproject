package org.example.service.security;

import lombok.RequiredArgsConstructor;
import org.example.entities.GymWorker;
import org.example.repositories.worker_repo.GymWorkerRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final GymWorkerRepository gymWorkerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GymWorker worker = gymWorkerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(
                worker.getUsername(),
                worker.getPassword(),
                List.of(new SimpleGrantedAuthority(worker.getRole().getRole()))
        );
    }
}
