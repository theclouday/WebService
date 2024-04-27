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
    private String author;
    private Long authorId;

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYearOfIssue(Integer yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getYearOfIssue() {
        return yearOfIssue;
    }

    public String getTitle() {
        return title;
    }
}
