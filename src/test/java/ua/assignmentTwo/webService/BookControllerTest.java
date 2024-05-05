package ua.assignmentTwo.webService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ua.assignmentTwo.webService.authors.dto.AuthorDetailsDto;
import ua.assignmentTwo.webService.authors.model.Author;
import ua.assignmentTwo.webService.authors.repository.AuthorRepository;
import ua.assignmentTwo.webService.books.dto.BookUploadDto;
import ua.assignmentTwo.webService.books.model.Book;
import ua.assignmentTwo.webService.books.repository.BookRepository;
import ua.assignmentTwo.webService.books.service.BookService;
import ua.assignmentTwo.webService.dto.PageDto;
import ua.assignmentTwo.webService.dto.UploadResultDto;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private ObjectMapper objectMapper;
    private Author author;
    private Book book;

    @AfterEach
    public void deleteTestData() {
        bookRepository.deleteAll();
    }

    @BeforeEach
    public void createAuthor() {
        author = new Author();
        author.setName("Max");
        author.setSurname("Parkinson");
        authorRepository.save(author);
    }

    @BeforeEach
    public void createBook() {
        book = new Book();
        book.setTitle("Invincibility");
        book.setYearOfIssue(2003);
        book.setAuthorId(author.getId());
        bookRepository.save(book);
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

        Book updatedBook = bookRepository.findAllById(book.getId());
        assertThat(updatedBook.getTitle()).isEqualTo(newTitle);
        assertThat(updatedBook.getYearOfIssue()).isEqualTo(newYearOfIssue);
    }

    @Test
    public void testDeleteBook() throws Exception {
        mvc.perform(delete("/api/books/" + book.getId()))
                .andExpect(status().isOk());

        Book deletedBook = bookRepository.findAllById(book.getId());
        assertThat(deletedBook).isNull();
    }

    @Test
    public void testGetList() throws Exception {
        int page = 0;
        int size = 100;
        int expectedTotalPages = 0;

        String body = """
                {
                     "authorId": %d,
                     "page": %d,
                     "size": %d
                 }
                """.formatted(author.getId(), page, size);

        MvcResult result = mvc.perform(post("/api/books/_list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        PageDto pageDto = objectMapper.readValue(content, PageDto.class);

        assertEquals(expectedTotalPages, pageDto.getTotalPages());
    }

    @Test
    public void testGetReport() throws Exception {
        Integer page = 0;
        Integer size = 10;
        String expectedFileName = "report.csv";

        String body = """
                {
                    "page": %d,
                    "size": %d,
                    "authorId": %d
                }""".formatted(page, size, author.getId());

        MvcResult result = mvc.perform(post("/api/books/_report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)).
                andExpect(status().isOk())
                .andReturn();

        String contentDispositionHeader = result.getResponse().getHeader(HttpHeaders.CONTENT_DISPOSITION);
        assertTrue(contentDispositionHeader.contains(expectedFileName));
    }

    private static List<BookUploadDto> getBookUploadDto() {
        AuthorDetailsDto author = new AuthorDetailsDto();
        author.setSurname("Parkinson");
        author.setName("Alex");

        BookUploadDto validBook = new BookUploadDto();
        validBook.setId(1L);
        validBook.setTitle("Lion");
        validBook.setYearOfIssue(1999);
        validBook.setAuthorId(author.getId());
        validBook.setAuthor(author);

        BookUploadDto invalidBook = new BookUploadDto();
        invalidBook.setId(2L);
        invalidBook.setTitle("Moon");

        List<BookUploadDto> uploadDtoList = List.of(validBook, invalidBook);
        return uploadDtoList;
    }

    @Test
    public void testUploadFromFile() throws Exception {
        List<BookUploadDto> uploadDtoList = getBookUploadDto();
        byte[] fileBytes = objectMapper.writeValueAsBytes(uploadDtoList);

        MockMultipartFile file = new MockMultipartFile("file", "bookTest.json", "application/json", fileBytes);

        UploadResultDto resultDto = bookService.uploadFromFile(file);

        assertEquals(1, resultDto.getSuccessCount());
        assertEquals(1, resultDto.getFailsCount());
    }
}
