package org.example.controllers.people_controllers;

import lombok.RequiredArgsConstructor;
import org.example.mappers.GymPersonMapper;
import org.example.repositories.GymPersonRepository;
import org.example.repositories.GymSeasonTicketRepository;
import org.example.requests.CreatePersonRequest;
import org.example.service.gym_people.photo.GymPersonPhotoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/people")
@RequiredArgsConstructor
public class GymPersonController {

    private final GymPersonRepository gymPersonRepository;
    private final GymSeasonTicketRepository gymSeasonTicketRepository;
    private final GymPersonMapper gymPersonMapper;
    private final GymPersonPhotoService gymPersonPhotoService;

    // пагинация
    // сортировка
    // фильтры
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("people", gymPersonRepository.findAll());
        return "people";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("seasonTickets", gymSeasonTicketRepository.findAll());
        return "add-person";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addPersonForm(@ModelAttribute CreatePersonRequest request,
                                @RequestParam("photo") MultipartFile photo) throws IOException {
        var ticketId = request.getSeasonTicketId() != null ? request.getSeasonTicketId() : 1;
        var person = gymPersonMapper.createFromRequest(request);
        person.setPhoto(Base64.getEncoder().encodeToString(photo.getBytes()));
        person.setSeasonTicket(gymSeasonTicketRepository.findById(ticketId));
        gymPersonRepository.save(person);
        return "redirect:/people";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/profile/{id}")
    public String showProfile(@PathVariable int id, Model model) {
        model.addAttribute("person", gymPersonRepository.getGymPersonById(id));
        model.addAttribute("seasonTickets", gymSeasonTicketRepository.findAll());
        return "print_person";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody @Validated CreatePersonRequest request) {
        var person = gymPersonRepository.findById(request.getId())
                .orElse(null);

        if (person == null) {
            return ResponseEntity.badRequest().body("Person not found");
        }

        gymPersonMapper.updateFromRequest(request, person);

        if (request.getSeasonTicketId() != null) {
            var ticket = gymSeasonTicketRepository.findById(request.getSeasonTicketId()).orElse(null);
            person.setSeasonTicket(ticket);
        } else {
            person.setSeasonTicket(gymSeasonTicketRepository.getGymSeasonTicketById(2));
        }

        gymPersonRepository.save(person);
        return ResponseEntity.ok("updated");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<?> deletePerson(@RequestParam int personId) {
        if (gymPersonRepository.existsById(personId)) {
            gymPersonRepository.deleteById(personId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/sendEmail")
    public String showForm(@RequestParam("id") int id, Model model) {
        model.addAttribute("id", id);
        return "send_email";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/upload-photo")
    @ResponseBody
    public ResponseEntity<String> uploadPersonPhoto(@RequestParam("id") int id, @RequestParam("file") MultipartFile file) throws IOException {
        try{
            gymPersonPhotoService.uploadPhoto(file, id);
            return ResponseEntity.ok("Photo saved");
        }
        catch (IllegalArgumentException | IOException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
