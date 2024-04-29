package ua.assignmentTwo.webService.authors.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthorDetailsDto {
    private Long id;
    private String name;
    private String surname;
}
