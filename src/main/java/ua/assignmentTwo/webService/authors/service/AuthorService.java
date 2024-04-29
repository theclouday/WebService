package ua.assignmentTwo.webService.authors.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.assignmentTwo.webService.authors.repository.Author;
import ua.assignmentTwo.webService.authors.repository.AuthorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public List<Author> searchAll() {
        return authorRepository.findAll();
    }

    public Author createAuthor(Author authors) {
        return authorRepository.save(authors);
    }

    public Author updateDataInAuthor(Long id, Author authorData) {
        Author authorToUpdate = authorRepository.findAllById(id);

        authorToUpdate.setName(authorData.getName());
        authorToUpdate.setSurname(authorData.getSurname());
        return authorRepository.save(authorToUpdate);
    }

    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }
}
