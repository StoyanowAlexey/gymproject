package org.example.controllers.people.sort;

import lombok.RequiredArgsConstructor;
import org.example.entities.GymVisitor;
import org.example.repositories.people_repo.GymVisitorRepository;
import org.example.service.gym_people.sort.SortVisitorsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class SortPeopleByName {
    public final GymVisitorRepository gymVisitorRepository;
    public final SortVisitorsService sortVisitorsService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/people/sortByName")
    public String sortPeopleByName(@RequestParam (name="dir", required = false, defaultValue = "asc") String direction, Model model){
        List<GymVisitor> gymVisitorList = (Objects.equals("desc", direction)) ? sortVisitorsService.returnDescendingSortedListOfPeople() : sortVisitorsService.returnAscendingSortedListOfPeople();
        model.addAttribute("people", gymVisitorList);
        model.addAttribute("direction", direction);
        model.addAttribute("sortField", "name");
        return "people";
        //осталось добавить моодел атрибуті і туда передать заодно лист
    }
}
