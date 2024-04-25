package ua.assignmentTwo.webService.Books.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.assignmentTwo.webService.Authors.Repository.AuthorRepository;
import ua.assignmentTwo.webService.Books.Repository.Book;
import ua.assignmentTwo.webService.Books.Repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository book;
    private final AuthorRepository author;

    public List<Book> findById(Long bookId) {
        List<Book> bookData = book.findAllById(bookId);
        return bookData;
    }

    public Book createBook(Book books) {
        return book.save(books);
    }

    public Book updateDataById(Book books) {
        return book.save(books);
    }

    public void deleteBookById(Long id) {
//        List<Book> bookToDelete = book.findAllById(id);
        book.deleteById(String.valueOf(id));
    }
}
