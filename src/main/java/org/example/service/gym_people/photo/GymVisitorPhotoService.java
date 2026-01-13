package org.example.service.gym_people.photo;

import lombok.RequiredArgsConstructor;
import org.example.repositories.people_repo.GymVisitorRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class GymVisitorPhotoService {
    private final GymVisitorRepository gymVisitorRepository;

    public void uploadPhoto(MultipartFile file, int personId) throws IOException {
        if (file.isEmpty() || !gymVisitorRepository.existsById(personId)) {
            throw new IllegalArgumentException();
        }
        var person = gymVisitorRepository.getGymPersonById(personId);
        person.setPhoto(Base64.getEncoder().encodeToString(file.getBytes()));
        gymVisitorRepository.save(person);
    }
}
