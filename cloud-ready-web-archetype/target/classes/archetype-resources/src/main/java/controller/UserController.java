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
@RequestMapping("/users")
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
            URI.create("/users/" + createdUser.getId())
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