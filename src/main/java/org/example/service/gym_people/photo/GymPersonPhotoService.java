package org.example.service.gym_people.photo;

import lombok.RequiredArgsConstructor;
import org.example.repositories.GymPersonRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class GymPersonPhotoService {
    private final GymPersonRepository gymPersonRepository;

    public void uploadPhoto(MultipartFile file, int personId) throws IOException {
        if (file.isEmpty() || !gymPersonRepository.existsById(personId)) {
            throw new IllegalArgumentException();
        }
        var person = gymPersonRepository.getGymPersonById(personId);
        person.setPhoto(Base64.getEncoder().encodeToString(file.getBytes()));
        gymPersonRepository.save(person);
    }
}
