package ua.assignmentTwo.webService.Authors.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.assignmentTwo.webService.Authors.repository.Author;
import ua.assignmentTwo.webService.Authors.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping("/authors")
    public List<Author> findAll() {
        return authorService.searchAll();
    }

    @PostMapping("/author")
    public Author createAuthorInDB(@RequestBody Author author){
        return authorService.createAuthor(author);
    }

    @PutMapping("/author/{id}")
    public Author updateAuthor(@PathVariable Long id, @RequestBody Author authorData){
        return authorService.updateDataInAuthor(id, authorData);
    }

    @DeleteMapping("/author/{id}")
    public void deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthorById(id);
    }
}
