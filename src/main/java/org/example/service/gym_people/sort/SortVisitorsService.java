package org.example.service.gym_people.sort;

import lombok.RequiredArgsConstructor;
import org.example.entities.GymVisitor;
import org.example.repositories.people_repo.GymVisitorRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SortVisitorsService {
    public final GymVisitorRepository gymVisitorRepository;

    public List<GymVisitor> returnDescendingSortedListOfPeople(){
        List<GymVisitor> people = gymVisitorRepository.findAll();
        people.sort(Comparator.comparing(GymVisitor:: getName).reversed());
        return people;
    }

    public List<GymVisitor> returnAscendingSortedListOfPeople(){
        List <GymVisitor> people = gymVisitorRepository.findAll();
        people.sort(Comparator.comparing(GymVisitor:: getName));
        return people;
    }
}
