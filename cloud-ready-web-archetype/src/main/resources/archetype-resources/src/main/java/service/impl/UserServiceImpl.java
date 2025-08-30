package ${package}.service.impl;

import ${package}.domain.User;
import ${package}.dto.UserDto;
import ${package}.dto.PageDto;
import ${package}.exception.ResourceNotFoundException;
import ${package}.repository.UserRepository;
import ${package}.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PageDto<UserDto> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return PageDto.from(users, this::convertToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public PageDto<UserDto> findBySearchTerm(String search, Pageable pageable) {
        Page<User> users = userRepository.findBySearchTerm(search, pageable);
        return PageDto.from(users, this::convertToDto);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "users", key = "#id")
    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id).map(this::convertToDto);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "users", key = "#username")
    public Optional<UserDto> findByUsername(String username) {
        return userRepository.findByUsername(username).map(this::convertToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> findByEmail(String email) {
        return userRepository.findByEmail(email).map(this::convertToDto);
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public UserDto create(UserDto userDto) {
        User user = convertToEntity(userDto);
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    @Override
    @CacheEvict(value = "users", key = "#id")
    public UserDto update(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        existingUser.setUsername(userDto.getUsername());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setFirstName(userDto.getFirstName());
        existingUser.setLastName(userDto.getLastName());
        existingUser.setEnabled(userDto.getEnabled());
        existingUser.setRoles(userDto.getRoles());

        User updatedUser = userRepository.save(existingUser);
        return convertToDto(updatedUser);
    }

    @Override
    @CacheEvict(value = "users", key = "#id")
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private UserDto convertToDto(User user) {
        return UserDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .fullName(user.getFullName())
            .enabled(user.getEnabled())
            .roles(user.getRoles())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();
    }

    private User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEnabled(userDto.getEnabled() != null ? userDto.getEnabled() : true);
        if (userDto.getRoles() != null) {
            user.setRoles(userDto.getRoles());
        }
        return user;
    }
}