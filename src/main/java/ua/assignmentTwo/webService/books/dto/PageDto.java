package ua.assignmentTwo.webService.books.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class PageDto {

    private List<BookListItemDto> bookListItemDto;
    private Integer totalPages;
}
