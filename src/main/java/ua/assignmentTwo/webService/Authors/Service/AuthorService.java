package ua.assignmentTwo.webService.Authors.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.assignmentTwo.webService.Authors.Repository.Author;
import ua.assignmentTwo.webService.Authors.Repository.AuthorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository author;

    public List<Author> searchAll() {
        return author.findAll();
    }

    public Author createAuthor(Author authors) {
        return author.save(authors);
    }
}
