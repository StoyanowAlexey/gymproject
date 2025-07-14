package org.example;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.entities.GymPerson;
import org.example.entities.GymWorker;
import org.example.entities.enums.Gender;
import org.example.repositories.GymPersonRepository;
import org.example.repositories.GymSeasonTicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.Mapping;

@Service
@RequiredArgsConstructor
public class MappingUtils {
    //private final GymSeasonTicketRepository gymSeasonTicketRepository;

    /*public TestDTO mapToTestDTO(GymPerson gymPerson) {
        TestDTO DTO = new TestDTO();
        DTO.setId(gymPerson.getId());
        DTO.setAge(gymPerson.getAge());
        DTO.setGender(gymPerson.getGender().toString());
        DTO.setName(gymPerson.getName());
        DTO.setTelegramAccount(gymPerson.getTelegramAccount());
        DTO.setSeasonTicketId(gymPerson.getSeasonTicket().getId());
        return DTO;
    }


    public GymPerson mapToGymPersonEntity(TestDTO DTO) {
        GymPerson gymPerson = new GymPerson();
        gymPerson.setId(DTO.getId());
        gymPerson.setName(DTO.getName());
        gymPerson.setAge(DTO.getAge());
        gymPerson.setTelegramAccount(DTO.getTelegramAccount());
        gymPerson.setGender(Gender.valueOf(DTO.getGender()));
        gymPerson.setSeasonTicket(gymSeasonTicketRepository.findById(DTO.getId()));
        return gymPerson;
    }*/
}
