package org.example.repositories;

import org.example.entities.GymRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GymRoleRepository extends JpaRepository<GymRole, Integer> {
    boolean existsGymRoleByRole(String role);

    GymRole findByRole(String role);

    GymRole findById(int id);

}
