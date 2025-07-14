package org.example.repositories;

import org.example.entities.GymSeasonTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GymSeasonTicketRepository extends JpaRepository<GymSeasonTicket, Integer> {
       GymSeasonTicket getGymSeasonTicketById(int id);

       boolean existsByTicketType(String ticketType);

       GymSeasonTicket findByTicketType(String ticketType);

       GymSeasonTicket findById(int id);

}
