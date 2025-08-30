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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    void getUserById_ShouldReturnUser_WhenUserExists() throws Exception {
        // Given
        Long userId = 1L;
        UserDto userDto = UserDto.builder()
            .id(userId)
            .username("testuser")
            .email("test@example.com")
            .firstName("Test")
            .lastName("User")
            .enabled(true)
            .build();

        when(userService.findById(userId)).thenReturn(Optional.of(userDto));

        // When & Then
        mockMvc.perform(get("/users/{id}", userId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(userId))
            .andExpect(jsonPath("$.username").value("testuser"))
            .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getUserById_ShouldReturnNotFound_WhenUserDoesNotExist() throws Exception {
        // Given
        Long userId = 999L;
        when(userService.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/users/{id}", userId))
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createUser_ShouldReturnCreatedUser_WhenValidInput() throws Exception {
        // Given
        UserDto inputDto = UserDto.builder()
            .username("newuser")
            .email("new@example.com")
            .firstName("New")
            .lastName("User")
            .enabled(true)
            .build();

        UserDto createdDto = UserDto.builder()
            .id(1L)
            .username("newuser")
            .email("new@example.com")
            .firstName("New")
            .lastName("User")
            .enabled(true)
            .build();

        when(userService.create(any(UserDto.class))).thenReturn(createdDto);

        // When & Then
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDto)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.username").value("newuser"));
    }

    @Test
    void getUserById_ShouldReturnUnauthorized_WhenNotAuthenticated() throws Exception {
        // When & Then
        mockMvc.perform(get("/users/1"))
            .andExpect(status().isUnauthorized());
    }
}