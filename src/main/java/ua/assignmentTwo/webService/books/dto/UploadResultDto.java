package ua.assignmentTwo.webService.books.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadResultDto {
    private int successCount;
    private int failsCount;
}
