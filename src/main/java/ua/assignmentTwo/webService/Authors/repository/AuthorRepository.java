package ua.assignmentTwo.webService.Authors.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findAllById(Long id);
}