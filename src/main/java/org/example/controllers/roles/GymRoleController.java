package org.example.controllers.roles;

import lombok.RequiredArgsConstructor;
import org.example.DTOs.GymRoleDTO;
import org.example.entities.GymRole;
import org.example.mappers.GymRoleMapper;
import org.example.repositories.role_repo.GymRoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GymRoleController {
    private final GymRoleRepository gymRoleRepository;
    private final GymRoleMapper gymRoleMapper;

    @PostMapping("/addNewGymRole")
    public GymRole addNewGymRole(@RequestBody GymRoleDTO gymRoleDTO){
        return gymRoleRepository.save(gymRoleMapper.toEntity(gymRoleDTO));
    }

    @PatchMapping ("/updateRoleById/{id}")
    public ResponseEntity<String> updateRoleById(@PathVariable int id, @RequestBody GymRoleDTO gymRoleDTO){
         if (!gymRoleRepository.existsById(id)) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role with id  " + id + " not exist");
         }
         try{
             GymRole gymRole = gymRoleMapper.toEntity(gymRoleDTO);
             gymRoleRepository.save(gymRole);

             return ResponseEntity.ok().body("Person was successfully updated");
         }
         catch (Exception e){
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating person : " + e.getMessage());
         }

    }

    @DeleteMapping("/deleteRoleById/{id}")
    public ResponseEntity deleteRoleById(@PathVariable int id){
        if (!gymRoleRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role with this id  " + id + " not found ");
        }
        try{
            GymRole gymRole = gymRoleRepository.findById(id);
            gymRoleRepository.delete(gymRole);
            return ResponseEntity.ok().body("Role was successfully deleted");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting person : " + e.getMessage());
        }
    }

    @GetMapping("/getAllRoles")
    public List<GymRole> getAllRoles(){
        return gymRoleRepository.findAll();
    }
}
