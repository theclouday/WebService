package ua.assignmentTwo.webService.books.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookUpdateDto {
    private String title;
    private Integer yearOfIssue;
}
