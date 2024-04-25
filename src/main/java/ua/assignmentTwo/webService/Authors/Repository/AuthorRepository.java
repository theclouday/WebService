package ua.assignmentTwo.webService.Authors.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.assignmentTwo.webService.Books.Repository.Book;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findAllById(Long id);
}