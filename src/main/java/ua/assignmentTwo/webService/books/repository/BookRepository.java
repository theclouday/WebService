package ua.assignmentTwo.webService.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.assignmentTwo.webService.books.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findAllById(Long id);
}
