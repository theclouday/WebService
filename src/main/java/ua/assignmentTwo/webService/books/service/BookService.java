package ua.assignmentTwo.webService.books.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ua.assignmentTwo.webService.authors.dto.AuthorDetailsDto;
import ua.assignmentTwo.webService.authors.model.Author;
import ua.assignmentTwo.webService.authors.repository.AuthorRepository;
import ua.assignmentTwo.webService.books.dto.*;
import ua.assignmentTwo.webService.books.model.Book;
import ua.assignmentTwo.webService.books.repository.BookRepository;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    private final ObjectMapper objectMapper;

    public BookDetailsDto getBookWithDetails(Long bookId) {
        Book book = bookRepository.findAllById(bookId);
        Author author = authorRepository.findAllById(book.getAuthorId());
        return convertDetails(book, author);
    }

    private BookDetailsDto convertDetails(Book book, Author author) {
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

    public void createBook(BookCreateDto bookCreateDto) {
        Book book = new Book();

        book.setId(bookCreateDto.getId());
        book.setTitle(bookCreateDto.getTitle());
        book.setYearOfIssue(bookCreateDto.getYearOfIssue());
        validate(book, bookCreateDto);
        bookRepository.save(book);
    }
    private void validate (Book book, BookCreateDto bookCreateDto) {
        try {
            if(bookCreateDto.getAuthor().getId() != null) {
                book.setAuthorId(bookCreateDto.getAuthor().getId());
            }
        }catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateDataInBook(Long bookId, BookUpdateDto bookUpdateDto) {
        Book bookToUpdate = bookRepository.findAllById(bookId);

        bookToUpdate.setTitle(bookUpdateDto.getTitle());
        bookToUpdate.setYearOfIssue(bookUpdateDto.getYearOfIssue());
        bookRepository.save(bookToUpdate);
    }

    public PageDto getList(BookListRequestDto requestDto) {
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getSize());
        Specification<Book> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("authorId"), requestDto.getAuthorId()));
            predicates.add(cb.like(root.get("title"), "%" + requestDto.getTitle() + "%"));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Page<Book> bookPage = bookRepository.findAll(specification, pageable);
        List<BookListItemDto> bookListItemDto = bookPage.getContent()
                .stream()
                .map(this::convertToListItem)
                .collect(Collectors.toList());

        PageDto pageDto = new PageDto();
        pageDto.setBookListItemDto(bookListItemDto);
        pageDto.setTotalPages(bookPage.getTotalPages());

        return pageDto;
    }

    private BookListItemDto convertToListItem(Book book) {
        BookListItemDto bookListItemDto = new BookListItemDto();
        Author author = authorRepository.findAllById(book.getAuthorId());

        bookListItemDto.setId(book.getId());
        bookListItemDto.setAuthorName(author.getName());
        bookListItemDto.setTitle(book.getTitle());
        bookListItemDto.setAuthorId(author.getId());
        return bookListItemDto;
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    public UploadResultDto uploadFromFile(MultipartFile file) {
        try {
            byte[] fileBytes = file.getBytes();
            List<BookUploadDto> uploadDtoList = objectMapper.readValue(fileBytes, new TypeReference<List<BookUploadDto>>() {
            });
            List<Optional<Book>> books = uploadDtoList
                    .stream()
                    .map(this::convertFromUpload)
                    .toList();
            List<Book> validBooks = books.stream().filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
            bookRepository.saveAll(validBooks);
            return counter(books);
        } catch (IOException e) {
            throw new RuntimeException("Error processing file upload: " + e.getMessage());
        }
    }

    private Optional<Book> convertFromUpload(BookUploadDto uploadDto) {
        try {
            Book book = new Book();

            book.setId(uploadDto.getId());
            book.setTitle(uploadDto.getTitle());
            book.setYearOfIssue(uploadDto.getYearOfIssue());
            book.setAuthorId(uploadDto.getAuthor().getId());

            return Optional.of(book);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private UploadResultDto counter(List<Optional<Book>> bookList) {
        UploadResultDto result = new UploadResultDto();

        int successCount = 0;
        int failCount = 0;

        for (Optional<Book> book : bookList) {
            if (book.isPresent()) {
                successCount++;
            } else {
                failCount++;
            }
        }
        result.setSuccessCount(successCount);
        result.setFailsCount(failCount);

        return result;
    }
}
