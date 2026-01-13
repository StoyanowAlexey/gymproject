package org.example.repositories.people_repo;

import org.example.entities.GymVisitor;
import org.example.entities.GymSeasonTicket;
import org.example.entities.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GymVisitorRepository extends JpaRepository<GymVisitor, Integer> {
    boolean existsByName(String name);

    Optional <GymVisitor> getGymPersonByPhoneNumber(String phoneNumber);

    GymVisitor findGymPersonByPhoneNumber(String phoneNumber);

    GymVisitor getGymUserByName(String name);

    List<GymVisitor> findAllBySeasonTicket_TicketType(String ticketType);

    List<GymVisitor> findAllBySeasonTicket(GymSeasonTicket seasonTicket);

    GymVisitor getGymPersonById(int id);

    void deleteByName(String name);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String gmail);

    GymVisitor getGymPersonByEmail(String gmail);

    int countBySeasonTicket_TicketType(String ticketType);

    int countByGender(Gender gender);
}
