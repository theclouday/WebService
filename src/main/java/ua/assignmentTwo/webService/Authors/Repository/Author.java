package ua.assignmentTwo.webService.Authors.Repository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
public class Author {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;
}
