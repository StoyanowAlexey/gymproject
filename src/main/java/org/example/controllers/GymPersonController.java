package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.SimpleMessage;
import org.example.DTOs.GymPersonDTO;
import org.example.TestDTO;
import org.example.entities.GymPerson;
import org.example.mappers.GymPersonMapper;
import org.example.repositories.GymPersonRepository;
import org.example.repositories.GymSeasonTicketRepository;
import org.hibernate.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class GymPersonController {
    public final GymPersonRepository gymPersonRepository;
    public final GymSeasonTicketRepository gymSeasonTicketRepository;
    public final GymPersonMapper gymPersonMapper;
    public final JavaMailSender javaMailSender;

    @GetMapping("/printAllPersonsBySeasonTicket/{seasonTicketId}")
    public List<GymPerson> printAllPersonsBySeasonTicket(@PathVariable int seasonTicketId) {
        return gymPersonRepository.findAllBySeasonTicket(gymSeasonTicketRepository.findById(seasonTicketId));
    }

    @PostMapping("/postNewPerson")
    public GymPerson addNewPerson(@RequestBody GymPersonDTO gymPersonDTO) {
        return gymPersonMapper.toEntity(gymPersonDTO);
    }

    @DeleteMapping("/deletePersonById/{id}")
    public ResponseEntity<String> deletePersonById(@PathVariable int id) {
        if (!gymPersonRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person with this " + id + " is not found");
        }
        try {
            gymPersonRepository.deleteById(id);
            return ResponseEntity.ok("Person with ID " + id + " deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting person: " + e.getMessage());
        }
    }

    @PostMapping(("/sendPersonEmail"))
    public ResponseEntity<String> sendPersonEmail(@RequestParam int id) {
        if (Objects.equals(gymPersonRepository.getGymPersonById(id).getGmail(), null))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person with this " + gymPersonRepository.getGymPersonById(id).getGmail() + " not found ");
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(gymPersonRepository.getGymPersonById(id).getGmail());
            message.setSubject("Sho ti Golova");
            message.setText("akte0n tut Bil");
            message.setFrom("rojbels@gmail.com");
            javaMailSender.send(message);
            return ResponseEntity.ok("Email was successfully sent ");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email wast not sent ");
        }

    }

    //base 64 image
    //https://www.baeldung.com/java-base64-encode-and-decode
    @PostMapping("/getPersonsImage")
    public ResponseEntity<String> getPersonsImage(@RequestParam int id, @RequestParam("file")MultipartFile multipartFile){
        if (multipartFile.isEmpty() || !gymPersonRepository.existsById(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("MultipartFile was not send!");
        try{
            GymPerson gymPerson = gymPersonRepository.getGymPersonById(id);
            gymPerson.setPhoto(Base64.getEncoder().withoutPadding().encodeToString(multipartFile.getBytes()));
            gymPersonRepository.save(gymPerson);
            return ResponseEntity.ok("Photo was successfully added! ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    @PatchMapping("/updatePersonInformation/{id}")
    public ResponseEntity<String> updatePerson(@PathVariable int id, @RequestBody GymPersonDTO gymPersonDTO) {
        if (!gymPersonRepository.existsById(id))
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person with this " + id + " is not found");
        try {
            GymPerson updatedPerson = gymPersonMapper.toEntity(gymPersonDTO);
            updatedPerson.setId(id);

            System.out.println("ID              -               " + id);
            System.out.println(updatedPerson);

            gymPersonRepository.save(updatedPerson);
            return ResponseEntity.ok("Person with ID " + id + " successfully updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating person: " + e.getMessage());
        }
    }

    @GetMapping("/getAllPersons")
    public List<GymPerson> getAllPersons() {
        return gymPersonRepository.findAll();
    }
}
