package ua.assignmentTwo.webService.books.dto;


import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import ua.assignmentTwo.webService.authors.dto.AuthorDetailsDto;

@Getter
@Setter
public class BookDetailsDto {
    private Long id;
    private String title;
    private Integer yearOfIssue;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorDetailsDto author;
}
