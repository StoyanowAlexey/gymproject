package org.example.repositories;

import org.example.entities.GymRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GymRoleRepository extends JpaRepository<GymRole, Integer> {
    boolean existsGymRoleByRole(String role);

    GymRole findByRole(String role);

    GymRole findById(int id);

}
