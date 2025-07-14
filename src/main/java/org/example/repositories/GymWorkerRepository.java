package org.example.repositories;

import org.example.entities.GymPerson;
import org.example.entities.GymRole;
import org.example.entities.GymWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GymWorkerRepository extends JpaRepository<GymWorker, Integer> {
       GymWorker findGymPersonByName(String name);
       boolean existsByName(String name);

       List<GymWorker> findAllByRole_Role(String role);

       List<GymWorker> findByRole_Id(int id);

       GymWorker findById(int id);

       //void updateGymPersonById(int id, GymWorker gymPerson);

}
