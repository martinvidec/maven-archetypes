# Technical Specification Part 3: DTOs, Controllers, Testing and Deployment

## Data Transfer Objects (DTOs)

### User DTO

```java
// dto/UserDto.java
package ${package}.dto;

import ${package}.domain.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Set;

@Schema(description = "User data transfer object")
public class UserDto {

    @Schema(description = "User ID", example = "1")
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Schema(description = "Username", example = "johndoe", required = true)
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Schema(description = "Email address", example = "john.doe@example.com", required = true)
    private String email;

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    @Schema(description = "First name", example = "John", required = true)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    @Schema(description = "Last name", example = "Doe", required = true)
    private String lastName;

    @Schema(description = "Full name", example = "John Doe", accessMode = Schema.AccessMode.READ_ONLY)
    private String fullName;

    @Schema(description = "Account enabled status", example = "true")
    private Boolean enabled;

    @Schema(description = "User roles", example = "[\"USER\", \"ADMIN\"]")
    private Set<Role> roles;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Creation timestamp", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Last update timestamp", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    // Constructors
    public UserDto() {}

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UserDto userDto = new UserDto();

        public Builder id(Long id) {
            userDto.id = id;
            return this;
        }

        public Builder username(String username) {
            userDto.username = username;
            return this;
        }

        public Builder email(String email) {
            userDto.email = email;
            return this;
        }

        public Builder firstName(String firstName) {
            userDto.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            userDto.lastName = lastName;
            return this;
        }

        public Builder fullName(String fullName) {
            userDto.fullName = fullName;
            return this;
        }

        public Builder enabled(Boolean enabled) {
            userDto.enabled = enabled;
            return this;
        }

        public Builder roles(Set<Role> roles) {
            userDto.roles = roles;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            userDto.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            userDto.updatedAt = updatedAt;
            return this;
        }

        public UserDto build() {
            return userDto;
        }
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }

    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
```

### Page DTO

```java
// dto/PageDto.java
package ${package}.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Schema(description = "Paginated response wrapper")
public class PageDto<T> {

    @Schema(description = "List of items")
    private List<T> content;

    @Schema(description = "Current page number (0-based)", example = "0")
    private int page;

    @Schema(description = "Page size", example = "20")
    private int size;

    @Schema(description = "Total number of elements", example = "100")
    private long totalElements;

    @Schema(description = "Total number of pages", example = "5")
    private int totalPages;

    @Schema(description = "Whether this is the first page", example = "true")
    private boolean first;

    @Schema(description = "Whether this is the last page", example = "false")
    private boolean last;

    @Schema(description = "Number of elements in current page", example = "20")
    private int numberOfElements;

    @Schema(description = "Whether the page is empty", example = "false")
    private boolean empty;

    // Constructors
    public PageDto() {}

    public PageDto(List<T> content, int page, int size, long totalElements, int totalPages,
                   boolean first, boolean last, int numberOfElements, boolean empty) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.first = first;
        this.last = last;
        this.numberOfElements = numberOfElements;
        this.empty = empty;
    }

    // Factory method to create from Spring Data Page
    public static <T, U> PageDto<U> from(Page<T> page, Function<T, U> mapper) {
        List<U> content = page.getContent().stream()
            .map(mapper)
            .collect(Collectors.toList());

        return new PageDto<>(
            content,
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.isFirst(),
            page.isLast(),
            page.getNumberOfElements(),
            page.isEmpty()
        );
    }

    // Getters and setters
    public List<T> getContent() { return content; }
    public void setContent(List<T> content) { this.content = content; }

    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

    public long getTotalElements() { return totalElements; }
    public void setTotalElements(long totalElements) { this.totalElements = totalElements; }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

    public boolean isFirst() { return first; }
    public void setFirst(boolean first) { this.first = first; }

    public boolean isLast() { return last; }
    public void setLast(boolean last) { this.last = last; }

    public int getNumberOfElements() { return numberOfElements; }
    public void setNumberOfElements(int numberOfElements) { this.numberOfElements = numberOfElements; }

    public boolean isEmpty() { return empty; }
    public void setEmpty(boolean empty) { this.empty = empty; }
}
```

### Error Response DTO

```java
// dto/ErrorResponseDto.java
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
```

## Controller Layer

### Base Controller

```java
// controller/BaseController.java
package ${package}.controller;

import ${package}.config.AppProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public abstract class BaseController {

    protected final AppProperties appProperties;

    protected BaseController(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    protected Pageable createPageable(Integer page, Integer size, String sort, String direction) {
        int pageNumber = page != null ? page : 0;
        int pageSize = size != null ? 
            Math.min(size, appProperties.getPagination().getMaxPageSize()) : 
            appProperties.getPagination().getDefaultPageSize();

        if (sort != null && !sort.isEmpty()) {
            Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
            return PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sort));
        }

        return PageRequest.of(pageNumber, pageSize);
    }
}
```

### User Controller

```java
// controller/UserController.java
package ${package}.controller;

import ${package}.config.AppProperties;
import ${package}.dto.UserDto;
import ${package}.dto.PageDto;
import ${package}.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User management operations")
@SecurityRequirement(name = "bearer-jwt")
public class UserController extends BaseController {

    private final UserService userService;

    public UserController(UserService userService, AppProperties appProperties) {
        super(appProperties);
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve a paginated list of users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved users"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageDto<UserDto>> getAllUsers(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size") @RequestParam(required = false) Integer size,
            @Parameter(description = "Sort field") @RequestParam(required = false) String sort,
            @Parameter(description = "Sort direction (asc/desc)") @RequestParam(defaultValue = "asc") String direction,
            @Parameter(description = "Search term") @RequestParam(required = false) String search) {

        Pageable pageable = createPageable(page, size, sort, direction);
        
        PageDto<UserDto> users = search != null && !search.trim().isEmpty() ?
            userService.findBySearchTerm(search.trim(), pageable) :
            userService.findAll(pageable);

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve a user by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN') or authentication.name == @userService.findById(#id).orElse(new ${package}.dto.UserDto()).username")
    public ResponseEntity<UserDto> getUserById(
            @Parameter(description = "User ID") @PathVariable Long id) {
        
        return userService.findById(id)
            .map(user -> ResponseEntity.ok(user))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "Get user by username", description = "Retrieve a user by their username")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN') or authentication.name == #username")
    public ResponseEntity<UserDto> getUserByUsername(
            @Parameter(description = "Username") @PathVariable String username) {
        
        return userService.findByUsername(username)
            .map(user -> ResponseEntity.ok(user))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create user", description = "Create a new user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "409", description = "User already exists"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> createUser(
            @Parameter(description = "User data") @Valid @RequestBody UserDto userDto) {
        
        UserDto createdUser = userService.create(userDto);
        
        return ResponseEntity.created(
            URI.create("/api/users/" + createdUser.getId())
        ).body(createdUser);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Update an existing user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN') or authentication.name == @userService.findById(#id).orElse(new ${package}.dto.UserDto()).username")
    public ResponseEntity<UserDto> updateUser(
            @Parameter(description = "User ID") @PathVariable Long id,
            @Parameter(description = "Updated user data") @Valid @RequestBody UserDto userDto) {
        
        UserDto updatedUser = userService.update(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Delete a user by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "User ID") @PathVariable Long id) {
        
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/username/{username}")
    @Operation(summary = "Check username availability", description = "Check if username is available")
    @ApiResponse(responseCode = "200", description = "Check completed")
    public ResponseEntity<Boolean> checkUsernameExists(
            @Parameter(description = "Username to check") @PathVariable String username) {
        
        boolean exists = userService.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/exists/email/{email}")
    @Operation(summary = "Check email availability", description = "Check if email is available")
    @ApiResponse(responseCode = "200", description = "Check completed")
    public ResponseEntity<Boolean> checkEmailExists(
            @Parameter(description = "Email to check") @PathVariable String email) {
        
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
}
```

### Health Controller

```java
// controller/HealthController.java
package ${package}.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuator.health.HealthIndicator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/public/health")
@Tag(name = "Health", description = "Application health endpoints")
public class HealthController implements HealthIndicator {

    @Override
    public Health health() {
        return Health.up()
            .withDetail("status", "Application is running")
            .withDetail("version", getClass().getPackage().getImplementationVersion())
            .build();
    }

    @GetMapping
    @Operation(summary = "Health check", description = "Check application health status")
    @ApiResponse(responseCode = "200", description = "Application is healthy")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Health health = health();
        return ResponseEntity.ok(health.getDetails());
    }

    @GetMapping("/ready")
    @Operation(summary = "Readiness check", description = "Check if application is ready to serve traffic")
    @ApiResponse(responseCode = "200", description = "Application is ready")
    public ResponseEntity<Map<String, Object>> readinessCheck() {
        // Add custom readiness checks here (database connectivity, external services, etc.)
        return ResponseEntity.ok(Map.of(
            "status", "ready",
            "timestamp", System.currentTimeMillis()
        ));
    }

    @GetMapping("/live")
    @Operation(summary = "Liveness check", description = "Check if application is alive")
    @ApiResponse(responseCode = "200", description = "Application is alive")
    public ResponseEntity<Map<String, Object>> livenessCheck() {
        return ResponseEntity.ok(Map.of(
            "status", "alive",
            "timestamp", System.currentTimeMillis()
        ));
    }
}
```

## Exception Handling

### Global Exception Handler

```java
// exception/GlobalExceptionHandler.java
package ${package}.exception;

import ${package}.dto.ErrorResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String CORRELATION_ID_KEY = "correlationId";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        
        ErrorResponseDto errorResponse = createErrorResponse(
            "RESOURCE_NOT_FOUND",
            ex.getMessage(),
            HttpStatus.NOT_FOUND,
            request.getDescription(false)
        );

        logger.warn("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDto> handleBusinessException(
            BusinessException ex, WebRequest request) {
        
        ErrorResponseDto errorResponse = createErrorResponse(
            ex.getCode(),
            ex.getMessage(),
            HttpStatus.BAD_REQUEST,
            request.getDescription(false)
        );

        logger.warn("Business exception: {} - {}", ex.getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(
            MethodArgumentNotValidException ex, WebRequest request) {
        
        List<ErrorResponseDto.FieldError> fieldErrors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> new ErrorResponseDto.FieldError(
                error.getField(),
                error.getRejectedValue(),
                error.getDefaultMessage()
            ))
            .collect(Collectors.toList());

        ErrorResponseDto errorResponse = createErrorResponse(
            "VALIDATION_FAILED",
            "Validation failed for request",
            HttpStatus.BAD_REQUEST,
            request.getDescription(false)
        );
        errorResponse.setFieldErrors(fieldErrors);

        logger.warn("Validation failed: {}", fieldErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolationException(
            ConstraintViolationException ex, WebRequest request) {
        
        ErrorResponseDto errorResponse = createErrorResponse(
            "CONSTRAINT_VIOLATION",
            "Constraint violation: " + ex.getMessage(),
            HttpStatus.BAD_REQUEST,
            request.getDescription(false)
        );

        logger.warn("Constraint violation: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAccessDeniedException(
            AccessDeniedException ex, WebRequest request) {
        
        ErrorResponseDto errorResponse = createErrorResponse(
            "ACCESS_DENIED",
            "Access denied",
            HttpStatus.FORBIDDEN,
            request.getDescription(false)
        );

        logger.warn("Access denied: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleTypeMismatchException(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        
        ErrorResponseDto errorResponse = createErrorResponse(
            "TYPE_MISMATCH",
            "Invalid parameter type: " + ex.getValue(),
            HttpStatus.BAD_REQUEST,
            request.getDescription(false)
        );

        logger.warn("Type mismatch: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(
            Exception ex, WebRequest request) {
        
        ErrorResponseDto errorResponse = createErrorResponse(
            "INTERNAL_ERROR",
            "An unexpected error occurred",
            HttpStatus.INTERNAL_SERVER_ERROR,
            request.getDescription(false)
        );

        logger.error("Unexpected error: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    private ErrorResponseDto createErrorResponse(String code, String message, HttpStatus status, String path) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(code, message, status.value(), path);
        errorResponse.setCorrelationId(MDC.get(CORRELATION_ID_KEY));
        return errorResponse;
    }
}
```

### Custom Exceptions

```java
// exception/ResourceNotFoundException.java
package ${package}.exception;

public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

```java
// exception/BusinessException.java
package ${package}.exception;

public class BusinessException extends RuntimeException {
    
    private final String code;
    
    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }
    
    public BusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
    
    public String getCode() {
        return code;
    }
}
```

## Testing Framework

### Application Test Configuration

```yaml
# application-test.yml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password: password
  
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    database-platform: org.hibernate.dialect.H2Dialect
  
  h2:
    console:
      enabled: true
  
  flyway:
    enabled: false
  
  data:
    redis:
      host: localhost
      port: 6370 # Different port for test Redis if needed
  
  cache:
    type: simple # Use simple cache for testing

logging:
  level:
    ${package}: DEBUG
    org.springframework.security: DEBUG

management:
  endpoints:
    web:
      exposure:
        include: "*"
```

### Integration Test Base Class

```java
// integration/BaseIntegrationTest.java
package ${package}.integration;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
public abstract class BaseIntegrationTest {

    @LocalServerPort
    protected int port;

    @Container
    static PostgreSQLContainer<?> postgresql = new PostgreSQLContainer<>("postgres:15")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("test");

    @Container
    static GenericContainer<?> redis = new GenericContainer<>("redis:7-alpine")
        .withExposedPorts(6379);

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresql::getJdbcUrl);
        registry.add("spring.datasource.username", postgresql::getUsername);
        registry.add("spring.datasource.password", postgresql::getPassword);
        
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379));
    }

    @BeforeEach
    void setUp() {
        // Common setup for integration tests
    }

    protected String getBaseUrl() {
        return "http://localhost:" + port;
    }
}
```

### Controller Test Example

```java
// controller/UserControllerTest.java
package ${package}.controller;

import ${package}.dto.UserDto;
import ${package}.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetUserById() throws Exception {
        // Given
        UserDto userDto = UserDto.builder()
            .id(1L)
            .username("testuser")
            .email("test@example.com")
            .firstName("Test")
            .lastName("User")
            .build();

        when(userService.findById(1L)).thenReturn(Optional.of(userDto));

        // When & Then
        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.username").value("testuser"))
            .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateUser() throws Exception {
        // Given
        UserDto inputDto = UserDto.builder()
            .username("newuser")
            .email("new@example.com")
            .firstName("New")
            .lastName("User")
            .build();

        UserDto createdDto = UserDto.builder()
            .id(1L)
            .username("newuser")
            .email("new@example.com")
            .firstName("New")
            .lastName("User")
            .build();

        when(userService.create(any(UserDto.class))).thenReturn(createdDto);

        // When & Then
        mockMvc.perform(post("/api/users")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDto)))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", "/api/users/1"))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.username").value("newuser"));
    }

    @Test
    void shouldReturnUnauthorizedForProtectedEndpoint() throws Exception {
        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isUnauthorized());
    }
}
```

## Containerization

### Dockerfile

```dockerfile
# Dockerfile
FROM eclipse-temurin:17-jre-alpine AS base

# Create a non-root user
RUN addgroup -g 1001 appgroup && adduser -u 1001 -G appgroup -s /bin/sh -D appuser

# Set working directory
WORKDIR /app

# Copy application jar
COPY target/*.jar app.jar

# Change ownership of the app directory
RUN chown -R appuser:appgroup /app

# Switch to non-root user
USER appuser

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/api/public/health || exit 1

# JVM tuning and application startup
ENTRYPOINT ["java", \
    "-XX:+UseG1GC", \
    "-XX:MaxRAMPercentage=75.0", \
    "-XX:+HeapDumpOnOutOfMemoryError", \
    "-XX:HeapDumpPath=/tmp/heapdump.hprof", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-Dspring.profiles.active=prod", \
    "-jar", "app.jar"]
```

### Docker Compose for Development

```yaml
# docker-compose.yml
version: '3.8'

services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=dev
      - DATABASE_URL=jdbc:postgresql://postgres:5432/${artifactId}_dev
      - DATABASE_USERNAME=${artifactId}_dev
      - DATABASE_PASSWORD=${artifactId}_dev
      - REDIS_HOST=redis
      - REDIS_PORT=6379
    depends_on:
      postgres:
        condition: service_healthy
      redis:
        condition: service_healthy
    networks:
      - app-network

  postgres:
    image: postgres:15-alpine
    environment:
      - POSTGRES_DB=${artifactId}_dev
      - POSTGRES_USER=${artifactId}_dev
      - POSTGRES_PASSWORD=${artifactId}_dev
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U ${artifactId}_dev -d ${artifactId}_dev"]
      interval: 10s
      timeout: 5s
      retries: 5
    networks:
      - app-network

  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"
    volumes:
      - redis_data:/data
    healthcheck:
      test: ["CMD", "redis-cli", "ping"]
      interval: 10s
      timeout: 3s
      retries: 5
    networks:
      - app-network

  prometheus:
    image: prom/prometheus:latest
    ports:
      - "9090:9090"
    volumes:
      - ./monitoring/prometheus.yml:/etc/prometheus/prometheus.yml
    networks:
      - app-network

  grafana:
    image: grafana/grafana:latest
    ports:
      - "3000:3000"
    environment:
      - GF_SECURITY_ADMIN_PASSWORD=admin
    volumes:
      - grafana_data:/var/lib/grafana
    networks:
      - app-network

volumes:
  postgres_data:
  redis_data:
  grafana_data:

networks:
  app-network:
    driver: bridge
```

## CI/CD Pipeline

### GitHub Actions Workflow

```yaml
# .github/workflows/ci.yml
name: CI/CD Pipeline

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

env:
  JAVA_VERSION: '17'
  MAVEN_OPTS: -Dmaven.repo.local=${{ github.workspace }}/.m2/repository

jobs:
  test:
    name: Test
    runs-on: ubuntu-latest
    
    services:
      postgres:
        image: postgres:15
        env:
          POSTGRES_DB: testdb
          POSTGRES_USER: test
          POSTGRES_PASSWORD: test
        options: >-
          --health-cmd pg_isready
          --health-interval 10s
          --health-timeout 5s
          --health-retries 5
        ports:
          - 5432:5432
      
      redis:
        image: redis:7
        options: >-
          --health-cmd "redis-cli ping"
          --health-interval 10s
          --health-timeout 5s
          --health-retries 5
        ports:
          - 6379:6379

    steps:
    - name: Checkout code
      uses: actions/checkout@v4

    - name: Set up JDK ${{ env.JAVA_VERSION }}
      uses: actions/setup-java@v4
      with:
        java-version: ${{ env.JAVA_VERSION }}
        distribution: 'temurin'

    - name: Cache Maven dependencies
      uses: actions/cache@v3
      with:
        path: ~/.m2
        key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}
        restore-keys: ${{ runner.os }}-m2

    - name: Run tests
      run: |
        mvn clean test \
          -Dspring.datasource.url=jdbc:postgresql://localhost:5432/testdb \
          -Dspring.datasource.username=test \
          -Dspring.datasource.password=test \
          -Dspring.data.redis.host=localhost \
          -Dspring.data.redis.port=6379

    - name: Run integration tests
      run: mvn verify -P integration-tests

    - name: Generate test report
      uses: dorny/test-reporter@v1
      if: success() || failure()
      with:
        name: Maven Tests
        path: target/surefire-reports/*.xml
        reporter: java-junit

    - name: Code coverage
      run: mvn jacoco:report

    - name: Upload coverage to Codecov
      uses: codecov/codecov-action@v3
      with:
        file: target/site/jacoco/jacoco.xml

  security:
    name: Security Scan
    runs-on: ubuntu-latest
    needs: test

    steps:
    - name: Checkout code
      uses: actions/checkout@v4

    - name: Set up JDK ${{ env.JAVA_VERSION }}
      uses: actions/setup-java@v4
      with:
        java-version: ${{ env.JAVA_VERSION }}
        distribution: 'temurin'

    - name: Cache Maven dependencies
      uses: actions/cache@v3
      with:
        path: ~/.m2
        key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}
        restore-keys: ${{ runner.os }}-m2

    - name: Run OWASP Dependency Check
      run: mvn dependency-check:check

    - name: Upload OWASP report
      uses: actions/upload-artifact@v3
      if: always()
      with:
        name: owasp-report
        path: target/dependency-check-report.html

  build:
    name: Build and Push Docker Image
    runs-on: ubuntu-latest
    needs: [test, security]
    if: github.ref == 'refs/heads/main'

    steps:
    - name: Checkout code
      uses: actions/checkout@v4

    - name: Set up JDK ${{ env.JAVA_VERSION }}
      uses: actions/setup-java@v4
      with:
        java-version: ${{ env.JAVA_VERSION }}
        distribution: 'temurin'

    - name: Cache Maven dependencies
      uses: actions/cache@v3
      with:
        path: ~/.m2
        key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}
        restore-keys: ${{ runner.os }}-m2

    - name: Build application
      run: mvn clean package -DskipTests

    - name: Log in to Docker Hub
      uses: docker/login-action@v3
      with:
        username: ${{ secrets.DOCKER_USERNAME }}
        password: ${{ secrets.DOCKER_PASSWORD }}

    - name: Build and push Docker image
      uses: docker/build-push-action@v5
      with:
        context: .
        push: true
        tags: |
          ${{ secrets.DOCKER_USERNAME }}/${artifactId}:latest
          ${{ secrets.DOCKER_USERNAME }}/${artifactId}:${{ github.sha }}

  deploy:
    name: Deploy to Staging
    runs-on: ubuntu-latest
    needs: build
    if: github.ref == 'refs/heads/main'
    environment: staging

    steps:
    - name: Deploy to staging
      run: |
        echo "Deploying to staging environment"
        # Add deployment steps here (e.g., kubectl, helm, etc.)
```

## Implementation Guidelines

### 1. Development Setup

1. **Prerequisites**:
   - Java 17 or later
   - Maven 3.9+
   - Docker and Docker Compose
   - IDE with Spring Boot support

2. **Local Development**:
   ```bash
   # Clone the generated project
   git clone <repository-url>
   cd <project-name>
   
   # Start dependencies
   docker-compose up postgres redis
   
   # Run the application
   ./mvnw spring-boot:run
   
   # Access application
   # API: http://localhost:8080/api
   # Swagger UI: http://localhost:8080/swagger-ui
   # Actuator: http://localhost:8080/actuator
   ```

3. **Testing**:
   ```bash
   # Unit tests
   ./mvnw test
   
   # Integration tests
   ./mvnw verify -P integration-tests
   
   # Code coverage
   ./mvnw jacoco:report
   ```

### 2. Configuration Management

- Use Spring Profiles for environment-specific configurations
- Externalize all secrets using environment variables
- Implement configuration validation using `@ConfigurationProperties`
- Use encrypted properties for sensitive data in non-production environments

### 3. Security Best Practices

- Always use HTTPS in production
- Implement proper input validation and sanitization
- Use parameterized queries to prevent SQL injection
- Configure CORS appropriately for your domain
- Implement rate limiting for public APIs
- Regular dependency vulnerability scanning

### 4. Monitoring and Observability

- Configure structured logging with correlation IDs
- Set up metrics collection with Micrometer
- Implement distributed tracing for microservices
- Configure alerts for critical metrics
- Use health checks for container orchestration

### 5. Performance Optimization

- Configure appropriate JVM settings for your environment
- Implement caching at multiple levels (application, database)
- Use connection pooling for database and external services
- Optimize database queries and add appropriate indexes
- Consider async processing for long-running operations

### 6. Deployment Strategies

- Use Blue-Green or Rolling deployments
- Implement database migration strategies
- Configure appropriate resource limits and requests
- Set up monitoring and alerting for production
- Plan for disaster recovery and backup strategies

This comprehensive technical specification provides all the necessary details for implementing a production-ready, cloud-native web application using Maven archetype. The specification includes complete code examples, configuration files, testing strategies, and deployment guidelines that enable a Copilot Agent to generate a fully functional application.