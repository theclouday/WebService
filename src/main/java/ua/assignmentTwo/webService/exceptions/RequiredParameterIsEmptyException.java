package ua.assignmentTwo.webService.exceptions;

public class RequiredParameterIsEmptyException extends RuntimeException {
    public RequiredParameterIsEmptyException(String message) {
        super(message);
    }
}
