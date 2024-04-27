package ua.assignmentTwo.webService.Books.Controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public Book createBookInDB(@RequestBody Book books) {
        return bookService.createBook(books);
    }

    @GetMapping("/book/{id}")
    public List<Book> findBookById(@PathVariable Long id) {
        return bookService.findBookWithAuthorById(id);
    }

    @PutMapping("/book/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book bookData) {
        return bookService.updateDataInBook(id, bookData);
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBookById(id);
            System.out.println("Done!");
        } catch (Exception e) {
            System.err.println("Error");
        }
    }

//    TODO
//     1)@PostMapping("/books/_list")
//     2)Переделать PUT запрос
//     3)Get не корректный
//     4)POST /api/entity1/_report
//     5)POST /api/entity1/upload


}
