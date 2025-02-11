package tbo.api.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErrorResponse(
        int status,
        String error,
        List<String> details
) {
    public ErrorResponse(HttpStatus status, String error) {
        this(status.value(), error, List.of());
    }

    public ErrorResponse(HttpStatus status, String error, List<String> details) {
        this(status.value(), error, details);
    }
}
