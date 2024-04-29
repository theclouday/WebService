package ua.assignmentTwo.webService.authors.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.assignmentTwo.webService.authors.repository.Author;
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
    public Author createAuthorInDB(@RequestBody Author author){
        return authorService.createAuthor(author);
    }

    @PutMapping("/{id}")
    public Author updateAuthor(@PathVariable Long id, @RequestBody Author authorData){
        return authorService.updateDataInAuthor(id, authorData);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthorById(id);
    }
}
