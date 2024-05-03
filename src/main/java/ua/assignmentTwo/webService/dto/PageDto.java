package ua.assignmentTwo.webService.dto;

import lombok.Getter;
import lombok.Setter;
import ua.assignmentTwo.webService.books.dto.BookListItemDto;

import java.util.List;
@Setter
@Getter
public class PageDto {
    private List<BookListItemDto> list;
    private Integer totalPages;
}
