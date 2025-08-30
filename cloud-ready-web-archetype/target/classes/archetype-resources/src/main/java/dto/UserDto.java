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