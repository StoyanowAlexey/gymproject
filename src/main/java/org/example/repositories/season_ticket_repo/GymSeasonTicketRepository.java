package org.example.repositories.season_ticket_repo;

import org.example.entities.GymSeasonTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GymSeasonTicketRepository extends JpaRepository<GymSeasonTicket, Integer> {
       GymSeasonTicket getGymSeasonTicketById(int id);

       boolean existsByTicketType(String ticketType);

       GymSeasonTicket findByTicketType(String ticketType);

       GymSeasonTicket findById(int id);

}
