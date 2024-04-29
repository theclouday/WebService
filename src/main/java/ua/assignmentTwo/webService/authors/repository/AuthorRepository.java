package ua.assignmentTwo.webService.authors.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findAllById(Long id);
}