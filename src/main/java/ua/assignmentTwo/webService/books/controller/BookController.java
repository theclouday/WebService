package ua.assignmentTwo.webService.books.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.assignmentTwo.webService.books.dto.*;
import ua.assignmentTwo.webService.books.service.BookService;
import ua.assignmentTwo.webService.dto.PageDto;
import ua.assignmentTwo.webService.dto.UploadResultDto;


@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping()
    public ResponseEntity createBookInDB(@RequestBody BookCreateDto bookCreateDto) {
        bookService.createBook(bookCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public BookDetailsDto findBookById(@PathVariable Long id) {
        return bookService.getBookWithDetails(id);
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable Long id, @RequestBody BookUpdateDto bookUpdateDto) {
        bookService.updateDataInBook(id, bookUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
    }

    @PostMapping("/_list")
    public PageDto getList(@RequestBody BookRequestDto bookRequestDto) {
        return bookService.getList(bookRequestDto);
    }

    @PostMapping(value = "/_report", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void getReport(HttpServletResponse response, @RequestBody BookRequestDto bookRequestDto) {
        bookService.generateReport(response, bookRequestDto);
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public UploadResultDto uploadFromFile(@RequestParam("file") MultipartFile multipart) {
        return bookService.uploadFromFile(multipart);
    }
}
