package kz.bmstu.kritinina.client;

import kz.bmstu.kritinina.config.FeignConfig;
import kz.bmstu.kritinina.auth.dto.UserCreateDto;
import kz.bmstu.kritinina.auth.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "auth", url = "http://auth-service:8110/api/v1/users", configuration = FeignConfig.class)
public interface UserClient {

    @PostMapping
    ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreateDto userCreateDto);

    @GetMapping("/me")
    ResponseEntity<UserResponseDto> getCurrentUser();

    @GetMapping
    ResponseEntity<List<UserResponseDto>> getAllUsers();
}