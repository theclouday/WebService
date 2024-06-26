package ua.assignmentTwo.webService.authors.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.assignmentTwo.webService.authors.dto.AuthorUpdateDto;
import ua.assignmentTwo.webService.authors.dto.AuthorCreateDto;
import ua.assignmentTwo.webService.authors.model.Author;
import ua.assignmentTwo.webService.authors.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@AllArgsConstructor
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping()
    public List<Author> findAll() {
        return authorService.searchAll();
    }

    @PostMapping()
    public ResponseEntity createAuthorInDB(@RequestBody AuthorCreateDto authorDto){
        authorService.createAuthor(authorDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public void updateAuthor(@PathVariable Long id, @RequestBody AuthorUpdateDto authorUpdateDto){
        authorService.updateDataInAuthor(id, authorUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthorById(id);
    }
}
