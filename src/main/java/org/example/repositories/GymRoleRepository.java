package org.example.repositories;

import org.example.entities.GymRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GymRoleRepository extends JpaRepository<GymRole, Integer> {
    boolean existsGymRoleByRole(String role);

    Optional<GymRole> findByRole(String role);

    GymRole findById(int id);

}
