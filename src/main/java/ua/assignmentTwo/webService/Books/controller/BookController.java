package ua.assignmentTwo.webService.Books.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.assignmentTwo.webService.Books.repository.Book;
import ua.assignmentTwo.webService.Books.service.BookService;

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
        bookService.deleteBookById(id);
    }

    @PostMapping("/book/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> uploadFromFile(@RequestParam("file") MultipartFile multipart){
        List<Book> uploadedBooks = bookService.uploadFromFile(multipart);
        return ResponseEntity.status(HttpStatus.CREATED).body("New data uploaded from file");
    }


//    TODO
//     Структора проекта?
//     1)@PostMapping("/books/_list")
//     3)Get не корректный в поле author
//     4)POST /api/entity1/_report
//     5)POST /api/entity1/upload доработать


}
