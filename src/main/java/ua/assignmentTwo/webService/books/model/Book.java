package ua.assignmentTwo.webService.books.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity(name = "books")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name = "year of issue")
    private Integer yearOfIssue;
    private Long authorId;
}
