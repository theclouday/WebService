package ua.assignmentTwo.webService.authors.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.assignmentTwo.webService.authors.dto.AuthorDetailsDto;
import ua.assignmentTwo.webService.authors.dto.AuthorUpdateDto;
import ua.assignmentTwo.webService.authors.dto.CreateAuthorDto;
import ua.assignmentTwo.webService.authors.model.Author;
import ua.assignmentTwo.webService.authors.repository.AuthorRepository;
import ua.assignmentTwo.webService.books.dto.UpdateBookDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public List<Author> searchAll() {
        return authorRepository.findAll();
    }

    public AuthorDetailsDto createAuthor(CreateAuthorDto createAuthorDto) {
        Author author = new Author();
        AuthorDetailsDto authorDetailsDto = new AuthorDetailsDto();

        author.setId(createAuthorDto.getId());
        author.setName(createAuthorDto.getName());
        author.setSurname(createAuthorDto.getSurname());
        authorRepository.save(author);

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
