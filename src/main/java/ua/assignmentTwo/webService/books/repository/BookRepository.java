package ua.assignmentTwo.webService.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.assignmentTwo.webService.books.model.Books;

public interface BookRepository extends JpaRepository<Books, Long> {
    Books findAllById(Long id);
}
