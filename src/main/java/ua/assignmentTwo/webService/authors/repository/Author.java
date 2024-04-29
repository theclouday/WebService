package ua.assignmentTwo.webService.authors.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
public class Author {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;
}
