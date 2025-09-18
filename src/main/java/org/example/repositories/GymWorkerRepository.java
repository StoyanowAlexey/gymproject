package org.example.repositories;

import org.example.entities.GymPerson;
import org.example.entities.GymRole;
import org.example.entities.GymWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GymWorkerRepository extends JpaRepository<GymWorker, Integer> {

       List<GymWorker> findAllByRole_Role(String role);

       List<GymWorker> findByRole_Id(int id);

       Optional<GymWorker> findByUsername(String username);

       //void updateGymPersonById(int id, GymWorker gymPerson);

}
