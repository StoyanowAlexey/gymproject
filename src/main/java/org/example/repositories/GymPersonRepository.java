package org.example.repositories;

import org.example.entities.GymPerson;
import org.example.entities.GymSeasonTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GymPersonRepository extends JpaRepository<GymPerson, Integer> {
    boolean existsByName(String name);

    Optional <GymPerson> getGymPersonByPhoneNumber(String phoneNumber);

    GymPerson getGymUserByName(String name);

    List<GymPerson> findAllBySeasonTicket_TicketType(String ticketType);

    List<GymPerson> findAllBySeasonTicket(GymSeasonTicket seasonTicket);

    GymPerson getGymPersonById(int id);

    void deleteByName(String name);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String gmail);

    GymPerson getGymPersonByEmail(String gmail);

}
