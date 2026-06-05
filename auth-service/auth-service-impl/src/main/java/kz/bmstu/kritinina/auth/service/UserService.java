package kz.bmstu.kritinina.auth.service;

import kz.bmstu.kritinina.auth.dto.UserCreateDto;
import kz.bmstu.kritinina.auth.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserCreateDto dto);
    UserResponseDto getCurrentUser(String username);
    List<UserResponseDto> getAllUsers();
}