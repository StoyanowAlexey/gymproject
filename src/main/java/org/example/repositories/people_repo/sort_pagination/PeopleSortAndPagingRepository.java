package org.example.repositories.people_repo.sort_pagination;

import org.example.entities.GymVisitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleSortAndPagingRepository extends JpaRepository<GymVisitor, Integer> {
}
