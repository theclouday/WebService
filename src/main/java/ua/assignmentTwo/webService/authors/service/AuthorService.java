package ua.assignmentTwo.webService.authors.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.assignmentTwo.webService.authors.dto.AuthorDetailsDto;
import ua.assignmentTwo.webService.authors.dto.AuthorUpdateDto;
import ua.assignmentTwo.webService.authors.dto.AuthorCreateDto;
import ua.assignmentTwo.webService.authors.model.Author;
import ua.assignmentTwo.webService.authors.repository.AuthorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public List<Author> searchAll() {
        return authorRepository.findAll();
    }

    public AuthorDetailsDto createAuthor(AuthorCreateDto authorCreateDto) {
        Author author = new Author();
        author.setId(authorCreateDto.getId());
        author.setName(authorCreateDto.getName());
        author.setSurname(authorCreateDto.getSurname());

        authorRepository.save(author);
        return convertToAuthor(author);
    }

    private AuthorDetailsDto convertToAuthor(Author author) {
        AuthorDetailsDto authorDetailsDto = new AuthorDetailsDto();
        authorDetailsDto.setId(author.getId());
        authorDetailsDto.setName(author.getName());
        authorDetailsDto.setSurname(author.getSurname());
        return authorDetailsDto;
    }

    public void updateDataInAuthor(Long id, AuthorUpdateDto authorUpdateDto) {
        Author authorToUpdate = authorRepository.findAllById(id);

        authorToUpdate.setName(authorUpdateDto.getName());
        authorToUpdate.setSurname(authorUpdateDto.getSurname());
        authorRepository.save(authorToUpdate);
    }

    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }
}
