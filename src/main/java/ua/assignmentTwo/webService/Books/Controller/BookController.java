package ua.assignmentTwo.webService.Books.Controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.assignmentTwo.webService.Authors.Repository.Author;
import ua.assignmentTwo.webService.Books.Repository.Book;
import ua.assignmentTwo.webService.Books.Service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/books")
    public Book createBookInDB(@RequestBody Book books){
        return bookService.createBook(books);
    }

    @GetMapping("/book/{id}")
    public List<Book> findBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }
}
