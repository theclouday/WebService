package ua.assignmentTwo.webService.Books.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findAllById(Long id);

    List<Book> findByAuthorId(Long authorId);

}
