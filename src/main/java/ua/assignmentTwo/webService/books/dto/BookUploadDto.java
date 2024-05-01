package ua.assignmentTwo.webService.books.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ua.assignmentTwo.webService.authors.dto.AuthorDetailsDto;

@Getter
@Setter
public class BookUploadDto {
    @NonNull
    @JsonProperty("id")
    private Long id;
    @NonNull
    @JsonProperty("title")
    private String title;
    @NonNull
    @JsonProperty("yearOfIssue")
    private Integer yearOfIssue;
    @NonNull
    private AuthorDetailsDto author;
    @NonNull
    private Long authorId;

}
