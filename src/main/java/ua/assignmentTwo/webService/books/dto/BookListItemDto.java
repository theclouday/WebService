package ua.assignmentTwo.webService.books.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookListItemDto {
    private Long id;
    private String title;
    private String authorName;
}
