package org.example;


import lombok.RequiredArgsConstructor;
import org.example.repositories.people_repo.sort_pagination.PeopleSortAndPagingRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/people")
public class TestController {
    public final PeopleSortAndPagingRepository peopleSortAndPagingRepository;

    /*@PostMapping("/test")
    public Page<GymVisitor> getAllGymPersonUsingPagination(@RequestBody PageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.toPageable();
        return peopleSortAndPagingRepository.findAll(pageable);
    }*/
}
