package ua.assignmentTwo.webService.books.dto;


import lombok.Getter;
import lombok.Setter;
import ua.assignmentTwo.webService.authors.dto.AuthorDetailsDto;

@Getter
@Setter
public class BookDetailsDto {
    private Long id;
    private String title;
    private Integer yearOfIssue;
    private AuthorDetailsDto author;
}
