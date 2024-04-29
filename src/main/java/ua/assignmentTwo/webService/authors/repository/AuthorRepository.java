package ua.assignmentTwo.webService.authors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.assignmentTwo.webService.authors.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findAllById(Long id);
}