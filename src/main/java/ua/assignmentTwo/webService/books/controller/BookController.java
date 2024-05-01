package ua.assignmentTwo.webService.books.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.assignmentTwo.webService.books.dto.BookDetailsDto;
import ua.assignmentTwo.webService.books.dto.BookListItemDto;
import ua.assignmentTwo.webService.books.dto.BookCreateDto;
import ua.assignmentTwo.webService.books.dto.BookUpdateDto;
import ua.assignmentTwo.webService.books.model.Book;
import ua.assignmentTwo.webService.books.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping()
    public ResponseEntity createBookInDB(@RequestBody BookCreateDto bookCreateDto) {
        bookService.createBook(bookCreateDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public BookDetailsDto findBookById(@PathVariable Long id) {
        return bookService.getBookWithDetails(id);
    }

    @PutMapping("/{id}")
    public void  updateBook(@PathVariable Long id, @RequestBody BookUpdateDto bookUpdateDto) {
        bookService.updateDataInBook(id, bookUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> uploadFromFile(@RequestParam("file") MultipartFile multipart){
        List<Book> uploadedBooks = bookService.uploadFromFile(multipart);
        return ResponseEntity.status(HttpStatus.CREATED).body("New data uploaded from file");
    }

    @PostMapping("/_list")
    public List<BookListItemDto> getList(){
        return bookService.getList();
    }


    /*TODO
       1) @PostMapping("/books/_list") доделать вывод и фильтр (через RequestBody {“entity2Id”: 2, …, “page”: 1, “size”: 20})
       2) POST /api/entity1/_report сделать
       3) POST /api/entity1/upload доработать генерацию JSON файла
       5) Тесты) MVCMock (1 вызов, 2 достать из бд)
     */


}
