package org.example.controllers.people;

import lombok.RequiredArgsConstructor;

import org.example.mappers.GymVisitorMapper;
import org.example.repositories.people_repo.GymVisitorRepository;

import org.example.repositories.season_ticket_repo.GymSeasonTicketRepository;
import org.example.requests.CreatePersonRequest;
import org.example.requests.PageRequestDTO;
import org.example.service.gym_people.photo.GymVisitorPhotoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class GymVisitorController {

    private final GymVisitorRepository gymVisitorRepository;
    private final GymSeasonTicketRepository gymSeasonTicketRepository;
    private final GymVisitorMapper gymVisitorMapper;
    private final GymVisitorPhotoService gymVisitorPhotoService;

    // пагинация
    // сортировка
    // фильтры
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String showAll(
            @ModelAttribute PageRequestDTO pageRequest,
            Model model
    ) {
        Sort sort = pageRequest.getDirection().equalsIgnoreCase("asc")
                ? Sort.by(pageRequest.getSortField()).ascending()
                : Sort.by(pageRequest.getSortField()).descending();

        Pageable pageable = PageRequest.of(
                pageRequest.getPageNo(),
                pageRequest.getPageSize(),
                sort
        );

        Page<?> peoplePage = gymVisitorRepository.findAll(pageable);

        model.addAttribute("people", peoplePage.getContent());
        model.addAttribute("page", peoplePage);
        model.addAttribute("pageRequest", pageRequest);

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
        var person = gymVisitorMapper.createFromRequest(request);
        person.setPhoto(Base64.getEncoder().encodeToString(photo.getBytes()));
        person.setSeasonTicket(gymSeasonTicketRepository.findById(ticketId));
        gymVisitorRepository.save(person);
        return "redirect:/people";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/profile/{id}")
    public String showProfile(@PathVariable int id, Model model) {
        model.addAttribute("person", gymVisitorRepository.getGymPersonById(id));
        model.addAttribute("seasonTickets", gymSeasonTicketRepository.findAll());
        return "print_person";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody @Validated CreatePersonRequest request) {
        var person = gymVisitorRepository.findById(request.getId())
                .orElse(null);

        if (person == null) {
            return ResponseEntity.badRequest().body("Person not found");
        }

        gymVisitorMapper.updateFromRequest(request, person);

        if (request.getSeasonTicketId() != null) {
            var ticket = gymSeasonTicketRepository.findById(request.getSeasonTicketId()).orElse(null);
            person.setSeasonTicket(ticket);
        } else {
            person.setSeasonTicket(gymSeasonTicketRepository.getGymSeasonTicketById(2));
        }

        gymVisitorRepository.save(person);
        return ResponseEntity.ok("updated");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<?> deletePerson(@RequestParam int personId) {
        if (gymVisitorRepository.existsById(personId)) {
            gymVisitorRepository.deleteById(personId);
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
            gymVisitorPhotoService.uploadPhoto(file, id);
            return ResponseEntity.ok("Photo saved");
        }
        catch (IllegalArgumentException | IOException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}