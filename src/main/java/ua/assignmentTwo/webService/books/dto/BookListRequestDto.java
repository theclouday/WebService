package ua.assignmentTwo.webService.books.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookListRequestDto {
    private Long authorId;
    private Integer page;
    private Integer size;
    private String authorName;
    private String title;

}
