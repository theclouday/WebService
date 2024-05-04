package ua.assignmentTwo.webService.authors.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ua.assignmentTwo.webService.authors.dto.AuthorUpdateDto;
import ua.assignmentTwo.webService.authors.dto.AuthorCreateDto;
import ua.assignmentTwo.webService.authors.model.Author;
import ua.assignmentTwo.webService.authors.repository.AuthorRepository;
import ua.assignmentTwo.webService.exceptions.RequiredParameterIsDuplicateException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public List<Author> searchAll() {
        return authorRepository.findAll();
    }

    public void createAuthor(AuthorCreateDto authorCreateDto) {
        Author author = new Author();

        author.setId(authorCreateDto.getId());
        author.setName(authorCreateDto.getName());
        author.setSurname(authorCreateDto.getSurname());
        uniquenessOfValues(author);
        authorRepository.save(author);
    }

    public void updateDataInAuthor(Long id, AuthorUpdateDto authorUpdateDto) {
        Author authorToUpdate = authorRepository.findAllById(id);

        uniquenessOfValues(authorUpdateDto);
        authorToUpdate.setName(authorUpdateDto.getName());
        authorToUpdate.setSurname(authorUpdateDto.getSurname());
        authorRepository.save(authorToUpdate);
    }

    @SneakyThrows
    private void uniquenessOfValues(Author author) {
        checkAuthorUniqueness(author.getName(), author.getSurname());
    }

    @SneakyThrows
    private void uniquenessOfValues(AuthorUpdateDto authorUpdateDto) {
        checkAuthorUniqueness(authorUpdateDto.getName(), authorUpdateDto.getSurname());
    }

    private void checkAuthorUniqueness(String name, String surname) {
        List<Author> authors = authorRepository.findAll();

        for (Author author : authors) {
            if (name.equals(author.getName()) && surname.equals(author.getSurname())) {
                throw new RequiredParameterIsDuplicateException("Field Name or Surname is duplicate");
            }
        }
    }

    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }
}
