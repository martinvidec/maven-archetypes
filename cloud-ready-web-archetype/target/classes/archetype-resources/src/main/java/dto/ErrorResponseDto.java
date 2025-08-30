package ${package}.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Error response")
public class ErrorResponseDto {

    @Schema(description = "Error code", example = "USER_NOT_FOUND")
    private String code;

    @Schema(description = "Error message", example = "User not found with id: 123")
    private String message;

    @Schema(description = "HTTP status code", example = "404")
    private int status;

    @Schema(description = "Request path", example = "/api/users/123")
    private String path;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Error timestamp")
    private LocalDateTime timestamp;

    @Schema(description = "Validation errors")
    private List<FieldError> fieldErrors;

    @Schema(description = "Correlation ID for request tracing", example = "abc123")
    private String correlationId;

    // Constructors
    public ErrorResponseDto() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponseDto(String code, String message, int status, String path) {
        this();
        this.code = code;
        this.message = message;
        this.status = status;
        this.path = path;
    }

    // Inner class for field validation errors
    public static class FieldError {
        @Schema(description = "Field name", example = "email")
        private String field;

        @Schema(description = "Invalid value", example = "invalid-email")
        private Object rejectedValue;

        @Schema(description = "Error message", example = "Email should be valid")
        private String message;

        public FieldError() {}

        public FieldError(String field, Object rejectedValue, String message) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.message = message;
        }

        // Getters and setters
        public String getField() { return field; }
        public void setField(String field) { this.field = field; }

        public Object getRejectedValue() { return rejectedValue; }
        public void setRejectedValue(Object rejectedValue) { this.rejectedValue = rejectedValue; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }

    // Getters and setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public List<FieldError> getFieldErrors() { return fieldErrors; }
    public void setFieldErrors(List<FieldError> fieldErrors) { this.fieldErrors = fieldErrors; }

    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }
}