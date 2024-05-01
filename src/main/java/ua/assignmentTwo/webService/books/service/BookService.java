package ua.assignmentTwo.webService.books.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.assignmentTwo.webService.authors.dto.AuthorDetailsDto;
import ua.assignmentTwo.webService.authors.model.Authors;
import ua.assignmentTwo.webService.authors.repository.AuthorRepository;
import ua.assignmentTwo.webService.books.dto.*;
import ua.assignmentTwo.webService.books.model.Books;
import ua.assignmentTwo.webService.books.repository.BookRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    private final ObjectMapper objectMapper;

    public BookDetailsDto getBookWithDetails(Long bookId) {
        Books book = bookRepository.findAllById(bookId);
        Authors author = authorRepository.findAllById(book.getAuthorId());
        return convertDetails(book, author);
    }

    private BookDetailsDto convertDetails(Books book, Authors author) {
        BookDetailsDto bookDetailsDto = new BookDetailsDto();
        AuthorDetailsDto authorDetailsDto = new AuthorDetailsDto();

        authorDetailsDto.setId(author.getId());
        authorDetailsDto.setName(author.getName());
        authorDetailsDto.setSurname(author.getSurname());

        bookDetailsDto.setId(book.getId());
        bookDetailsDto.setTitle(book.getTitle());
        bookDetailsDto.setYearOfIssue(book.getYearOfIssue());
        bookDetailsDto.setAuthor(authorDetailsDto);

        return bookDetailsDto;
    }

    public BookDetailsDto createBook(BookCreateDto bookCreateDto) {
        Books book = new Books();

        book.setId(bookCreateDto.getId());
        book.setTitle(bookCreateDto.getTitle());
        book.setYearOfIssue(bookCreateDto.getYearOfIssue());
        book.setAuthorId(bookCreateDto.getAuthor().getId());

        bookRepository.save(book);
        return convertToBookDetailsDto(book);
    }

    private BookDetailsDto convertToBookDetailsDto(Books book) {
        BookDetailsDto bookDetailsDto = new BookDetailsDto();
        AuthorDetailsDto authorDetailsDto = new AuthorDetailsDto();
        Authors author = new Authors();

        authorDetailsDto.setId(author.getId());
        authorDetailsDto.setName(author.getName());
        authorDetailsDto.setSurname(author.getSurname());

        bookDetailsDto.setId(book.getId());
        bookDetailsDto.setTitle(book.getTitle());
        bookDetailsDto.setYearOfIssue(book.getYearOfIssue());
        bookDetailsDto.setAuthor(authorDetailsDto);

        return bookDetailsDto;
    }

    public void updateDataInBook(Long bookId, BookUpdateDto bookUpdateDto) {
        Books bookToUpdate = bookRepository.findAllById(bookId);

        bookToUpdate.setTitle(bookUpdateDto.getTitle());
        bookToUpdate.setYearOfIssue(bookUpdateDto.getYearOfIssue());
        bookRepository.save(bookToUpdate);
    }

    public List<BookListItemDto> getList() {
        List<Books> booksList = bookRepository.findAll();
        System.out.println(booksList);
        Map<Long, Authors> authorMap = authorRepository.findAll().stream()
                .collect(Collectors.toMap(Authors::getId, authors -> authors));
        return booksList.stream()
                .map(book -> convertToListItem(book, authorMap.get(book.getAuthorId())))
                .toList();
    }

    private BookListItemDto convertToListItem(Books book, Authors author) {
        BookListItemDto bookListItemDto = new BookListItemDto();
        bookListItemDto.setId(book.getId());
        bookListItemDto.setAuthorName(author.getName());
        bookListItemDto.setTitle(book.getTitle());
        return bookListItemDto;
    }


    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Books> uploadFromFile(MultipartFile file) {
        try {
            byte[] fileBytes = file.getBytes();
            List<Books> books = objectMapper.readValue(fileBytes, new TypeReference<List<BookUploadDto>>() {
                    })
                    .stream()
                    .map(this::convertFromUpload)
                    .toList();
            return bookRepository.saveAll(books);
        } catch (IOException e) {
            throw new RuntimeException("Error processing file upload: " + e.getMessage());
        }
    }


    private Books convertFromUpload(BookUploadDto uploadDto) {
        Books book = new Books();

        book.setId(uploadDto.getId());
        book.setTitle(uploadDto.getTitle());
        book.setYearOfIssue(uploadDto.getYearOfIssue());
        book.setAuthorId(uploadDto.getAuthorId());

        return book;
    }
}
