package ua.assignmentTwo.webService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ua.assignmentTwo.webService.authors.model.Author;
import ua.assignmentTwo.webService.authors.repository.AuthorRepository;
import ua.assignmentTwo.webService.books.controller.BookController;
import ua.assignmentTwo.webService.books.model.Book;
import ua.assignmentTwo.webService.books.repository.BookRepository;
import ua.assignmentTwo.webService.books.service.BookService;

import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = WebServiceApplication.class)
@AutoConfigureMockMvc
public class BookControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Mock
    private BookService bookService;
    @Autowired
    private BookController bookController;

    @AfterEach
    public void afterEach() {
        bookRepository.deleteAll();
    }

    @Test
    public void testCreateBookInDb() throws Exception {
        String title = "Sun";
        int yearOfIssue = 1999;
        int authorId = 1;

        String body = """
                {
                    "title": "%s",
                    "yearOfIssue": %d,
                    "author": {
                        "id" : %d
                    }
                }
                """.formatted(title, yearOfIssue, authorId);
        mvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());

        Book book = bookRepository.findAll().get(0);

        assertThat(book).isNotNull();
        assertThat(book.getTitle()).isEqualTo(title);
        assertThat(book.getYearOfIssue()).isEqualTo(yearOfIssue);
        assertThat(book.getAuthorId()).isEqualTo(authorId);
    }

    @Test
    public void testCreateBook_validation() throws Exception {
        mvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testFindBookById() throws Exception {
        Author author = new Author();
        author.setName("Alex");
        author.setSurname("Volkanovski");
        authorRepository.save(author);

        Book book = new Book();
        book.setAuthorId(author.getId());
        book.setTitle("Sun");
        book.setYearOfIssue(1999);
        bookRepository.save(book);

        mvc.perform(get("/api/books/" + book.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(Integer.parseInt(String.valueOf(book.getId())))))
                .andExpect(jsonPath("$.title", is(book.getTitle())))
                .andExpect(jsonPath("$.yearOfIssue", is(book.getYearOfIssue())))
                .andExpect(jsonPath("$.author.id", is(Integer.parseInt(String.valueOf(author.getId())))))
                .andExpect(jsonPath("$.author.name", is(author.getName())))
                .andExpect(jsonPath("$.author.surname", is(author.getSurname())));
    }

    @Test
    public void testUpdateBook() throws Exception {
        Author author = new Author();
        author.setName("Alex");
        author.setSurname("Gowll");
        authorRepository.save(author);

        Book book = new Book();
        book.setTitle("Sun");
        book.setYearOfIssue(1999);
        book.setAuthorId(author.getId());
        bookRepository.save(book);

        String newTitle = "Moon";
        int newYearOfIssue = 2001;

        String body = """
                {
                    "title": "%s",
                    "yearOfIssue": %d
                }
                """.formatted(newTitle, newYearOfIssue);

        MockHttpServletRequestBuilder requestBuilder = put("/api/books/" + book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteBook() throws Exception {
        Author author = new Author();
        author.setName("Alex");
        author.setSurname("Gowll");
        authorRepository.save(author);

        Book book = new Book();
        book.setTitle("Sun");
        book.setYearOfIssue(1999);
        book.setAuthorId(author.getId());
        bookRepository.save(book);

        mvc.perform(delete("/api/books/" + book.getId()))
                .andExpect(status().isOk());
    }
}
