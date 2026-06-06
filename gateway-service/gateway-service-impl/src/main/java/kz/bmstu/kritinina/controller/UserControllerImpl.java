package kz.bmstu.kritinina.controller;

import kz.bmstu.kritinina.auth.api.UserController;
import kz.bmstu.kritinina.auth.dto.UserCreateDto;
import kz.bmstu.kritinina.auth.dto.UserResponseDto;
import kz.bmstu.kritinina.service.GatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final GatewayService gatewayService;

    @Override
    public ResponseEntity<UserResponseDto> createUser(UserCreateDto userCreateDto) {
        return ResponseEntity.ok(gatewayService.createUser(userCreateDto));
    }

    @Override
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        return ResponseEntity.ok(gatewayService.getCurrentUser());
    }

    @Override
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(gatewayService.getAllUsers());
    }
}