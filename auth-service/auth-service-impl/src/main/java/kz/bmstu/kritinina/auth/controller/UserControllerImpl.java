package kz.bmstu.kritinina.auth.controller;

import kz.bmstu.kritinina.auth.api.UserController;
import kz.bmstu.kritinina.auth.dto.UserCreateDto;
import kz.bmstu.kritinina.auth.dto.UserResponseDto;
import kz.bmstu.kritinina.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<UserResponseDto> createUser(UserCreateDto userCreateDto) {
        return ResponseEntity.ok(userService.createUser(userCreateDto));
    }

    @Override
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(userService.getCurrentUser(username));
    }

    @Override
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}