package org.example.service.gym_people.pagination;

import lombok.RequiredArgsConstructor;
import org.example.entities.GymVisitor;
import org.example.repositories.people_repo.sort_pagination.PeopleSortAndPagingRepository;
import org.example.requests.PageRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GymVisitorPaginationService {
    private final PeopleSortAndPagingRepository peopleSortAndPagingRepository;
    public Page<GymVisitor> findAllPeopleInPage(PageRequestDTO pageRequestDTO){
        Sort.Direction dir = Sort.Direction.fromString(pageRequestDTO.getDirection());
        Pageable pageable = PageRequest.of(pageRequestDTO.getPageNo(), pageRequestDTO.getPageSize(), Sort.by(dir, pageRequestDTO.getSortField()));
        return peopleSortAndPagingRepository.findAll(pageable);
    }
}
