package org.example.HTMLControllers;

import lombok.RequiredArgsConstructor;
import org.example.DTOs.GymPersonDTO;
import org.example.entities.GymPerson;
import org.example.entities.enums.Gender;
import org.example.mappers.GymPersonMapper;
import org.example.repositories.GymPersonRepository;
import org.example.repositories.GymSeasonTicketRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GymPersonHTMLController {
    public final GymPersonRepository gymPersonRepository;
    public final GymSeasonTicketRepository gymSeasonTicketRepository;
    public final GymPersonMapper gymPersonMapper;

    @GetMapping("/html/persons")
    public String printAllPersons(Model model){
        List<GymPerson> list = gymPersonRepository.findAll();
        model.addAttribute("persons", list);
        return "persons";
    }

    @GetMapping("/html/persons/add")
    public String showAddPersonForm(Model model) {
        model.addAttribute("seasonTickets", gymSeasonTicketRepository.findAll());
        return "add-person"; //
    }

    @PostMapping("/html/persons/add")
    public String addNewPerson(@RequestParam String name,
                               @RequestParam int age,
                               @RequestParam Gender gender,
                               @RequestParam int seasonTicketId,
                               @RequestParam String telegramAccount,
                               @RequestParam String phoneNumber,
                               @RequestParam String gmail
    ){
        GymPersonDTO gymPersonDTO = new GymPersonDTO(15, name , age, gender, gymSeasonTicketRepository.findById(seasonTicketId),  telegramAccount, phoneNumber, gmail);
        gymPersonRepository.save(gymPersonMapper.toEntity(gymPersonDTO));
        return "redirect:/html/persons";
    }

    @GetMapping("/getPersonsImage")
    public String showUploadForm() {
        return "upload_photo"; // thymeleaf шаблон з формою
    }

    @GetMapping("/html/persons/enterMenu")
    public String showFindForm() {
        return "find-person";
    }

    @PostMapping("html/persons/enterMenu")
    public String isPersonExist(@RequestParam String gmail, Model model){
        if(!gymPersonRepository.existsByGmail(gmail)) {
           model.addAttribute("error", "Wrong Number");
           return "find-person";
        }
        else {
            model.addAttribute("person", gymPersonRepository.getGymPersonByGmail(gmail));
            return "person-details";
        }
    }

    @GetMapping("/html/persons/delete")
    public String showDeletePerson(Model model) {
        model.addAttribute("persons",gymPersonRepository.findAll());
        return "delete-person"; //
    }

    @PostMapping("/html/persons/delete")
    public String deletePersonConfirmed(@RequestParam int personId){
        gymPersonRepository.deleteById(personId);
          return "redirect:/html/persons";
    }

}
