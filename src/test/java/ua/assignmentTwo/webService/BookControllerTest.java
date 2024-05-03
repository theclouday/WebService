package ua.assignmentTwo.webService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ua.assignmentTwo.webService.books.model.Book;
import ua.assignmentTwo.webService.books.repository.BookRepository;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
    private ObjectMapper objectMapper;

    @AfterEach
    public void afterEach() {
        bookRepository.deleteAll();
    }

    @Test
    public void testCreateBook() throws Exception {
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
                """.formatted(title,yearOfIssue,authorId);
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
}
