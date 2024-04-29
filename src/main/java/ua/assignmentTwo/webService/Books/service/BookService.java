package ua.assignmentTwo.webService.Books.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.assignmentTwo.webService.Authors.repository.Author;
import ua.assignmentTwo.webService.Authors.repository.AuthorRepository;
import ua.assignmentTwo.webService.Books.dto.BookUploadDto;
import ua.assignmentTwo.webService.Books.repository.Book;
import ua.assignmentTwo.webService.Books.repository.BookRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    private final ObjectMapper objectMapper;

    public List<Book> findBookWithAuthorById(Long bookId) {
        Book book = bookRepository.findAllById(bookId);
        Author author = authorRepository.findAllById(book.getAuthorId());

        book.setAuthor(String.valueOf(author));

        return Collections.singletonList(book);
    }

    public Book createBook(Book books) {
        return bookRepository.save(books);
    }

    public int saveAll(List<Book> books){
        int saved = 0;
        for (Book bk : books){
            ++saved;
        }
        return saved;
    }

    public Book updateDataInBook(Long bookId, Book bookData) {
        Book bookToUpdate = bookRepository.findAllById(bookId);

        bookToUpdate.setAuthor(bookData.getAuthor());
        bookToUpdate.setTitle(bookData.getTitle());
        bookToUpdate.setYearOfIssue(bookData.getYearOfIssue());

        return bookRepository.save(bookToUpdate);
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> uploadFromFile(MultipartFile file){
        try {
            byte[] fileBytes = file.getBytes();
            List<Book> books = objectMapper.readValue(fileBytes, new TypeReference<List<BookUploadDto>>() {})
                    .stream()
                    .map(this::convertFromUpload)
                    .toList();
            return bookRepository.saveAll(books);
        } catch (IOException e) {
            throw new RuntimeException("Error processing file upload: " + e.getMessage());
        }
    }


    private Book convertFromUpload(BookUploadDto uploadDto){
        Book book = new Book();
        book.setId(uploadDto.getId());
        book.setTitle(uploadDto.getTitle());
        book.setYearOfIssue(uploadDto.getYearOfIssue());
        book.setAuthorId(uploadDto.getAuthorId());
        return book;
    }
}
