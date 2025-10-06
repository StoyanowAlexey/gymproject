package org.example.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.DTOs.GymPersonDTO;
import org.example.UserExcelExporter;
import org.example.entities.GymPerson;
import org.example.mappers.GymPersonMapper;
import org.example.repositories.GymPersonRepository;
import org.example.repositories.GymSeasonTicketRepository;
import org.example.requests.CreatePersonRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/people")
@RequiredArgsConstructor
public class GymPersonController {

    private final GymPersonRepository gymPersonRepository;
    private final GymSeasonTicketRepository gymSeasonTicketRepository;
    private final GymPersonMapper gymPersonMapper;
    private final JavaMailSender javaMailSender;

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    // HTML: Головна сторінка
    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("people", gymPersonRepository.findAll());
        return "people";
    }
    // HTML: Форма додавання
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
    @GetMapping("/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<GymPerson> listUsers = gymPersonRepository.findAll();

        UserExcelExporter excelExporter = new UserExcelExporter(listUsers);

        excelExporter.export(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/enterMenu")
    public String enterForm() {
        return "find-person";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/enterMenu")
    public String findByGmail(@RequestParam String gmail, Model model) {
        if (!gymPersonRepository.existsByGmail(gmail)) {
            model.addAttribute("error", "Not found");
            return "find-person";
        }
        model.addAttribute("person", gymPersonRepository.getGymPersonByGmail(gmail));
        return "person-details";
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

        // оновлюємо тільки потрібні поля
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
    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam(value = "id") int id,
                            @RequestParam("message") String message) {
        var person = gymPersonRepository.getGymPersonById(id);
        if (person.getGmail() == null) return new NullPointerException().getMessage();
        try {
            var simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(person.getGmail());
            simpleMailMessage.setSubject("Jym Company Email");
            simpleMailMessage.setText(message);
            simpleMailMessage.setFrom("rojbels@gmail.com");
            javaMailSender.send(simpleMailMessage);
            return "redirect:/people";
        } catch (Exception e) {
            return e.getMessage();
        }
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
    public ResponseEntity<String> uploadPhoto(@RequestParam("id") int id, @RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty() || !gymPersonRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Invalid input");
        }
        var person = gymPersonRepository.getGymPersonById(id);
        person.setPhoto(Base64.getEncoder().encodeToString(file.getBytes()));
        gymPersonRepository.save(person);
        return ResponseEntity.ok("Photo saved");
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-json")
    @ResponseBody
    public List<GymPerson> getAll() {
        return gymPersonRepository.findAll();
    }
}
