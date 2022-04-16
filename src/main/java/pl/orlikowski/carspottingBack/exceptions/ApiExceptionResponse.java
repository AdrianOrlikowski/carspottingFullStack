package pl.orlikowski.carspottingBack.exceptions;

import org.springframework.http.HttpStatus;

public class ApiExceptionResponse {
    private final String message;
    private final HttpStatus status;

    public ApiExceptionResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
