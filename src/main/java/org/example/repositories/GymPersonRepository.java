package org.example.repositories;

import org.example.entities.GymPerson;
import org.example.entities.GymSeasonTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GymPersonRepository extends JpaRepository<GymPerson, Integer> {
    boolean existsByName(String name);

    GymPerson getGymUserByName(String name);

    List<GymPerson> findAllBySeasonTicket_TicketType(String ticketType);

    List<GymPerson> findAllBySeasonTicket(GymSeasonTicket seasonTicket);

    GymPerson getGymPersonById(int id);

    void deleteByName(String name);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByGmail(String gmail);

    GymPerson getGymPersonByGmail(String gmail);

}
