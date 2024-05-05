package ua.assignmentTwo.webService.books.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Predicate;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import ua.assignmentTwo.webService.authors.dto.AuthorDetailsDto;
import ua.assignmentTwo.webService.authors.model.Author;
import ua.assignmentTwo.webService.authors.repository.AuthorRepository;
import ua.assignmentTwo.webService.books.dto.*;
import ua.assignmentTwo.webService.books.model.Book;
import ua.assignmentTwo.webService.books.repository.BookRepository;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

import org.springframework.http.HttpHeaders;
import ua.assignmentTwo.webService.dto.PageDto;
import ua.assignmentTwo.webService.dto.UploadResultDto;
import ua.assignmentTwo.webService.exceptions.RequiredParameterIsEmptyException;

import java.nio.charset.StandardCharsets;
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
        validate(book);
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
        Author author = new Author();

        Optional.ofNullable(bookCreateDto.getAuthor())
                .orElseThrow(() -> new RequiredParameterIsEmptyException("Author details are required"));

        author.setId(bookCreateDto.getAuthor().getId());
        book.setTitle(bookCreateDto.getTitle());
        book.setYearOfIssue(bookCreateDto.getYearOfIssue());
        book.setAuthorId(author.getId());

        validate(book);
        bookRepository.save(book);
    }

    @SneakyThrows
    private void validate(Book book) {
        if (book == null) {
            throw new RequiredParameterIsEmptyException("There is no book with this ID");
        } else {
            if (book.getTitle() == null) {
                throw new RequiredParameterIsEmptyException("Title is required parameter");
            } else if (book.getYearOfIssue() == null) {
                throw new RequiredParameterIsEmptyException("Year of issue is required parameter");
            } else if (book.getAuthorId() == null) {
                throw new RequiredParameterIsEmptyException("Author Id is required parameter");
            }
        }
    }

    public void updateDataInBook(Long bookId, BookUpdateDto bookUpdateDto) {
        Book bookToUpdate = bookRepository.findAllById(bookId);
        bookToUpdate.setTitle(bookUpdateDto.getTitle());
        bookToUpdate.setYearOfIssue(bookUpdateDto.getYearOfIssue());
        validate(bookToUpdate);
        bookRepository.save(bookToUpdate);
    }

    public PageDto getList(BookRequestDto requestDto) {
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getSize());
        Specification<Book> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("authorId"), requestDto.getAuthorId()));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Page<Book> bookPage = bookRepository.findAll(specification, pageable);
        List<BookListItemDto> bookListItemDto = bookPage.getContent()
                .stream()
                .map(this::convertToListItem)
                .collect(Collectors.toList());

        PageDto pageDto = new PageDto();
        pageDto.setList(bookListItemDto);
        pageDto.setTotalPages(bookPage.getTotalPages());

        return pageDto;
    }

    private BookListItemDto convertToListItem(Book book) {
        BookListItemDto bookListItemDto = new BookListItemDto();
        Author author = authorRepository.findAllById(book.getAuthorId());

        bookListItemDto.setBookId(book.getId());
        bookListItemDto.setAuthorFullName(author.getName() + " " + author.getSurname());
        bookListItemDto.setTitle(book.getTitle());
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
            List<Book> validBooks = books.stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
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

    public void generateReport(HttpServletResponse response, BookRequestDto bookRequestDto) {
        Page<Book> books = filterRequestData(bookRequestDto);
        String csvContent = generateCsvContent(books);
        byte[] csvBytes = csvContent.getBytes(StandardCharsets.UTF_8);

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.csv");
        try {
            response.getOutputStream().write(csvBytes);
            response.getOutputStream().flush();
        } catch (IOException e) {
            throw new RuntimeException("Error generating csv file" + e.getMessage());
        }
    }

    private String generateCsvContent(Page<Book> books) {
        StringBuilder builder = new StringBuilder();
        builder.append("Id,Title,Year of issue,Author Id\n");

        for (Book book : books) {
            builder.append(book.getId()).append(",");
            builder.append(book.getTitle()).append(",");
            builder.append(book.getYearOfIssue()).append(",");
            builder.append(book.getAuthorId()).append("\n");
        }
        return builder.toString();
    }

    private Page<Book> filterRequestData(BookRequestDto bookRequestDto) {
        Long authorId = bookRequestDto.getAuthorId();
        String title = bookRequestDto.getTitle();
        Integer page = bookRequestDto.getPage();
        Integer size = bookRequestDto.getSize();

        PageRequest pageRequest = PageRequest.of(page, size);

        if (authorId != null) {
            return bookRepository.findByAuthorId(authorId, pageRequest);
        } else if (title != null) {
            return bookRepository.findByTitle(title, pageRequest);
        } else {
            return bookRepository.findAll(pageRequest);
        }
    }
}

