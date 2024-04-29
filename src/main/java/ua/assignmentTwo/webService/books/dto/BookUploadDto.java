package ua.assignmentTwo.webService.books.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookUploadDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("yearOfIssue")
    private Integer yearOfIssue;
    @JsonProperty("author")
    private String author;
    @JsonProperty("authorId")
    private Long authorId;
}
