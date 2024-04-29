package ua.assignmentTwo.webService.books.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private Integer yearOfIssue;
    private Long authorId;

}
