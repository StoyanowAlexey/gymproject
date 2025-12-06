package org.example.controllers.workers_controllers;


import lombok.RequiredArgsConstructor;
import org.example.DTOs.GymWorkerDTO;
import org.example.entities.GymWorker;
import org.example.mappers.GymWorkerMapper;
import org.example.repositories.GymWorkerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GymWorkerController {
    public final GymWorkerRepository gymWorkerRepository;
    public final GymWorkerMapper gymWorkerMapper;

    @GetMapping("/getAllWorkers")
    public List<GymWorker> getAllWorkers() {
        return gymWorkerRepository.findAll();
    }


    @PostMapping("/addNewWorker")
    public GymWorker addNewWorker(@RequestBody GymWorkerDTO gymWorkerDTO) {
        return gymWorkerRepository.save(gymWorkerMapper.toEntity(gymWorkerDTO));
    }

    @GetMapping("/getAllWorkersByRole/{id}")
    public List<GymWorker> getAllWorkersByRole(@PathVariable int id) {
        return gymWorkerRepository.findByRole_Id(id);
    }

    @DeleteMapping("/deleteWorkerById/{id}")
    public ResponseEntity<String> deleteWorkerById(@PathVariable int id) {
        if (!gymWorkerRepository.existsById(id)) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Worker with id  " + id + " not found ");
        }
        try {
            gymWorkerRepository.deleteById(id);
            return ResponseEntity.ok("Worker with id " + id + " was successfully deleted ");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error, worker was not deleted ");
        }
    }

    @PatchMapping("/updateWorkerById")
    public ResponseEntity<String> updateWorkerById(@RequestBody GymWorkerDTO gymWorkerDTO){
          if (!gymWorkerRepository.existsById(gymWorkerDTO.getId())){
              return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Worker with this id " + gymWorkerDTO.getId() + " not found ");
          }
          try{
              gymWorkerRepository.save(gymWorkerMapper.toEntity(gymWorkerDTO));
              return ResponseEntity.ok().body("Worker was successfully updated ");
          }
          catch (Exception e){
              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : Worker was not deleted ");
          }
    }
}
