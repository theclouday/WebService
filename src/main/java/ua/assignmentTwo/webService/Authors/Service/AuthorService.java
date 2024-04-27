package ua.assignmentTwo.webService.Authors.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.assignmentTwo.webService.Authors.Repository.Author;
import ua.assignmentTwo.webService.Authors.Repository.AuthorRepository;

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

    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }
}
