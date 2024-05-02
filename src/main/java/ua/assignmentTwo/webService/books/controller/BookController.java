package ua.assignmentTwo.webService.books.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.assignmentTwo.webService.books.dto.*;
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

    @PostMapping("/_list")
    public List<BookListItemDto> getList(@RequestBody BookListRequestDto bookListRequestDto){
        return bookService.getList();
    }

//    @PostMapping("/_report")
//    public ExcelDFileDto generateFile(RequestBody BookExcelRequest requesExceltDto) {
//        return null;
//    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public UploadResultDto uploadFromFile(@RequestParam("file") MultipartFile multipart){
        return  bookService.uploadFromFile(multipart);
    }



    /*TODO
       1) @PostMapping("/books/_list") доделать вывод и фильтр (через RequestBody {“entity2Id”: 2, …, “page”: 1, “size”: 20})
       2) POST /api/entity1/_report сделать
       5) Тесты) MVCMock (1 вызов, 2 достать из бд)
     */


}
