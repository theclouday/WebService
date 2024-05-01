package ua.assignmentTwo.webService.books.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ua.assignmentTwo.webService.authors.dto.AuthorDetailsDto;

@Getter
@Setter
public class BookUploadDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("yearOfIssue")
    private Integer yearOfIssue;
    private AuthorDetailsDto author;
    private Long authorId;
}
