package kz.bmstu.kritinina.auth.api;

import kz.bmstu.kritinina.auth.dto.UserCreateDto;
import kz.bmstu.kritinina.auth.dto.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/users")
public interface UserController {

    @PostMapping
    ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreateDto userCreateDto);

    @GetMapping("/me")
    ResponseEntity<UserResponseDto> getCurrentUser();

    @GetMapping
    ResponseEntity<List<UserResponseDto>> getAllUsers();
}