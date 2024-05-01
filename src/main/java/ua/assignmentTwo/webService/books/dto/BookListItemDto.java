package ua.assignmentTwo.webService.books.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookListItemDto {
    private Long id;
    private String title;
    private String authorName;
}
