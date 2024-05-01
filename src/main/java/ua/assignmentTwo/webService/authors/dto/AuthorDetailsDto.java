package ua.assignmentTwo.webService.authors.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthorDetailsDto {
    private Long id;
    private String name;
    private String surname;

    @Override
    public String toString() {
        return "id: " + getId() +
                "\nname: " + getName() +
                "\nsurname: " + getSurname();
    }


}

