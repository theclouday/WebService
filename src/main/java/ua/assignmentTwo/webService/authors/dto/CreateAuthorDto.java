package ua.assignmentTwo.webService.authors.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAuthorDto {
    private Long id;
    private String name;
    private String surname;
}
