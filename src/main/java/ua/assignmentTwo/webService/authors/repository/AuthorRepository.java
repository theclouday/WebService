package ua.assignmentTwo.webService.authors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.assignmentTwo.webService.authors.model.Authors;

public interface AuthorRepository extends JpaRepository<Authors, Long> {
    Authors findAllById(Long id);
}