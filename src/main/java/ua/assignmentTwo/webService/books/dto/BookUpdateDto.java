package ua.assignmentTwo.webService.books.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookUpdateDto {
    private String title;
    private Integer yearOfIssue;
}
