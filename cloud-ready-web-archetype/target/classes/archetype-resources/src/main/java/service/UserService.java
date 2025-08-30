package ${package}.service;

import ${package}.dto.UserDto;
import ${package}.dto.PageDto;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    PageDto<UserDto> findAll(Pageable pageable);

    PageDto<UserDto> findBySearchTerm(String search, Pageable pageable);

    Optional<UserDto> findById(Long id);

    Optional<UserDto> findByUsername(String username);

    Optional<UserDto> findByEmail(String email);

    UserDto create(UserDto userDto);

    UserDto update(Long id, UserDto userDto);

    void delete(Long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}