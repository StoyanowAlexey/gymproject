package org.example;

import lombok.RequiredArgsConstructor;
import org.example.entities.GymPerson;
import org.example.mappers.GymPersonMapper;
import org.example.repositories.GymPersonRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/api/demo")
public class Test {
    private final GymPersonRepository gymPersonRepository;
    private final MappingUtils mappingUtils;
    public final GymPersonMapper gymPersonMapper;

    /*@GetMapping("/")
    public String home() {
        return "index"; // Вернет index.html из папки templates
    }

    @PostMapping("/post")
    public String postMethod(){
        return "This is method Post";
    }
    @PutMapping("/put")
    public String putMethod(){
        return "This is method Put";
    }
    @DeleteMapping("/delete")
    public String deleteMethod(){
        return "This is method delete";
    }
    @RequestMapping()
    public GymPerson getGymPerson()*/
    @GetMapping("/get")
    public String getMethod() {
        return "This is method get";
    }

    @RequestMapping("/hello")
    public String hello() {
        return "Hello, Leshin cite ";
    }

    @GetMapping("/allUsers")
    public List<GymPerson> getAllUsers() {
        return gymPersonRepository.findAll();
    }

    /*@PostMapping("/postUser")
    public GymPerson newGymUser(@RequestBody TestDTO testDTO) {
          return gymPersonMapper.toEntity(testDTO);
    }*/

    //@DeleteMapping("/")
}
