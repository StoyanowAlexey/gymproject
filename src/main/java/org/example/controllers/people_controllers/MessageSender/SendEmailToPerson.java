package org.example.controllers.people_controllers.MessageSender;

import lombok.RequiredArgsConstructor;
import org.example.repositories.GymPersonRepository;
import org.example.service.gym_people.information_senders.EmailService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/people")
public class SendEmailToPerson {

    public final GymPersonRepository gymPersonRepository;
    private final EmailService emailService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam(value = "id") int id,
                            @RequestParam("message") String message) {
        var person = gymPersonRepository.getGymPersonById(id);
        if (person.getEmail() == null) return new NullPointerException().getMessage();
        try {
            emailService.sendEmail(person.getEmail(), message, "Gym Company");
            return "redirect:/people";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
