package org.example.controllers.season_ticket_controllers;


import lombok.RequiredArgsConstructor;
import org.example.DTOs.GymSeasonTicketDTO;
import org.example.entities.GymSeasonTicket;
import org.example.mappers.GymSeasonTicketMapper;
import org.example.repositories.GymSeasonTicketRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GymSeasonTicketController {
    public final GymSeasonTicketRepository gymSeasonTicketRepository;
    public final GymSeasonTicketMapper gymSeasonTicketMapper;


    @PostMapping("/addNewSeasonTicket")
    public GymSeasonTicket addNewSeasonTicket(@RequestBody GymSeasonTicketDTO gymSeasonTicketDTO) {
        return gymSeasonTicketRepository.save(gymSeasonTicketMapper.toEntity(gymSeasonTicketDTO));
    }

    @GetMapping("/getAllSeasonTickets")
    public List<GymSeasonTicket> getAllSeasonTickets() {
        return gymSeasonTicketRepository.findAll();
    }

    @DeleteMapping("/deleteSeasonTicketById/{id}")
    public ResponseEntity<String> deleteSeasonTicketById(@PathVariable int id) {
        if (!gymSeasonTicketRepository.existsById(id)) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Season Ticket with id " + id + " not found ");
        }
        try {
            gymSeasonTicketRepository.deleteById(id);
            return ResponseEntity.ok().body("Season Ticket was successfully deleted !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting Season Ticket : " + e.getMessage());
        }
    }

    @PatchMapping("/updateSeasonTicket")
    public ResponseEntity<String> updateSeasonTicket(@RequestBody GymSeasonTicketDTO gymSeasonTicketDTO) {
        if (!gymSeasonTicketRepository.existsById(gymSeasonTicketDTO.getId())) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Season Ticket was not found ");
        }
        try{
            gymSeasonTicketRepository.save(gymSeasonTicketMapper.toEntity(gymSeasonTicketDTO));
            return ResponseEntity.ok().body("SeasonTicket was successfully updated ");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Season Ticket was not updated ");
        }
    }

}
