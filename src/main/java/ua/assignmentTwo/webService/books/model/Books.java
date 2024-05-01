package ua.assignmentTwo.webService.books.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name = "year of issue")
    private Integer yearOfIssue;
    private Long authorId;
}
