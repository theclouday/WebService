package ua.assignmentTwo.webService.exceptions;

public class RequiredParameterIsDuplicateException extends RuntimeException {
    public RequiredParameterIsDuplicateException(String message) {
        super(message);
    }
}
