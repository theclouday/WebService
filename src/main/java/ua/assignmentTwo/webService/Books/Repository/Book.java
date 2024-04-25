package ua.assignmentTwo.webService.Books.Repository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class Book {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private Integer yearOfIssue;
    private Long authorId;

}
