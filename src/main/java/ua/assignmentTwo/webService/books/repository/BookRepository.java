package ua.assignmentTwo.webService.books.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.assignmentTwo.webService.books.model.Book;


public interface BookRepository extends JpaRepository<Book, Long> {
    Book findAllById(Long id);


    Page<Book> findAll(Specification<Book> specification, Pageable pageable);
}
