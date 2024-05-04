package ua.assignmentTwo.webService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ua.assignmentTwo.webService.authors.model.Author;
import ua.assignmentTwo.webService.authors.repository.AuthorRepository;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = WebServiceApplication.class)
@AutoConfigureMockMvc
public class AuthorControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private AuthorRepository authorRepository;

    @AfterEach
    public void afterEach() {
        authorRepository.deleteAll();
    }

    @Test
    public void testFindAll() throws Exception {
        List<Author> authors = authorRepository.findAll();

        mvc.perform(get("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertThat(authors).isNotNull();
    }

    @Test
    public void testCreateAuthorInDb() throws Exception {
        String name = "Alex";
        String surname = "Potter";

        String body = """
                {
                    "name": "%s",
                    "surname": "%s"
                }
                """.formatted(name, surname);
        mvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());

        Author author = authorRepository.findAll().get(0);
        assertThat(author).isNotNull();
        assertThat(author.getName()).isEqualTo(name);
        assertThat(author.getSurname()).isEqualTo(surname);
    }

    @Test
    public void testUpdateAuthor() throws Exception {
        Author author = new Author();
        author.setName("Alex");
        author.setSurname("Nealex");
        authorRepository.save(author);

        String newName = "John";
        String newSurname = "Garill";

        String body = """
                {
                    "name": "%s",
                    "surname": "%s"
                }
                """.formatted(newName, newSurname);

        MockHttpServletRequestBuilder requestBuilder = put("/api/authors/" + author.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteAuthor() throws Exception {
        Author author = new Author();
        author.setName("John");
        author.setSurname("Kennedy");
        authorRepository.save(author);

        mvc.perform(delete("/api/authors/" + author.getId()))
                .andExpect(status().isOk());
    }
}
