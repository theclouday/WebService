package ua.assignmentTwo.webService.Books.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.assignmentTwo.webService.Authors.Repository.Author;
import ua.assignmentTwo.webService.Authors.Repository.AuthorRepository;
import ua.assignmentTwo.webService.Books.Repository.Book;
import ua.assignmentTwo.webService.Books.Repository.BookRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public List<Book> findBookWithAuthorById(Long bookId) {
        Book book = bookRepository.findAllById(bookId);
        Author author = authorRepository.findAllById(book.getAuthorId());

        book.setAuthor(String.valueOf(author));

        return Collections.singletonList(book);
    }

    public Book createBook(Book books) {
        return bookRepository.save(books);
    }

    public Book updateDataInBook(Long bookId, Book bookData) {
        Book bookToUpdate = bookRepository.findAllById(bookId);

        bookToUpdate.setAuthor(bookData.getAuthor());
        bookToUpdate.setTitle(bookData.getTitle());
        bookToUpdate.setYearOfIssue(bookData.getYearOfIssue());

        return bookRepository.save(bookToUpdate);
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(String.valueOf(id));
    }
}
