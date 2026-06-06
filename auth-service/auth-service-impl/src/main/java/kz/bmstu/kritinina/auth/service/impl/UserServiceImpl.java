package kz.bmstu.kritinina.auth.service.impl;

import kz.bmstu.kritinina.auth.dto.UserCreateDto;
import kz.bmstu.kritinina.auth.dto.UserResponseDto;
import kz.bmstu.kritinina.auth.model.UserEntity;
import kz.bmstu.kritinina.auth.repository.UserRepository;
import kz.bmstu.kritinina.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    private Keycloak getKeycloakInstance() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm("master")
                .clientId("admin-cli")
                .grantType("password")
                .username("admin")
                .password("admin")
                .build();
    }

    @Override
    @Transactional
    public UserResponseDto createUser(UserCreateDto dto) {
        System.out.println("[DEBUG] Starting registration for: " + dto.getUsername());
        
        Keycloak keycloak = getKeycloakInstance();
        System.out.println("[DEBUG] Keycloak instance created");

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(dto.getPassword());
        credential.setTemporary(false);
        user.setCredentials(Collections.singletonList(credential));

        System.out.println("[DEBUG] Calling Keycloak API to create user...");
        Response response = keycloak.realm(realm).users().create(user);
        
        if (response.getStatus() != 201) {
            String errorMsg = "Status: " + response.getStatus();
            try { errorMsg += ", Body: " + response.readEntity(String.class); } catch (Exception ignored) {}
            System.err.println("[DEBUG] Keycloak failure: " + errorMsg);
            throw new RuntimeException("Failed to create user in Keycloak: " + errorMsg);
        }
        System.out.println("[DEBUG] User created in Keycloak successfully");

        List<UserRepresentation> search = keycloak.realm(realm).users().search(dto.getUsername());
        if (search.isEmpty()) {
            throw new RuntimeException("User created but not found in search");
        }
        String keycloakId = search.get(0).getId();
        System.out.println("[DEBUG] Found Keycloak ID: " + keycloakId);

        try {
            System.out.println("[DEBUG] Assigning role: " + dto.getRole());
            RoleRepresentation roleRep = keycloak.realm(realm).roles().get(dto.getRole()).toRepresentation();
            keycloak.realm(realm).users().get(keycloakId).roles().realmLevel().add(Collections.singletonList(roleRep));
            System.out.println("[DEBUG] Role assigned");
        } catch (Exception e) {
            System.err.println("[DEBUG] Role assignment error: " + e.getMessage());
        }

        UserEntity entity = UserEntity.builder()
                .id(UUID.fromString(keycloakId))
                .username(dto.getUsername())
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .role(dto.getRole())
                .departmentId(dto.getDepartmentId())
                .build();

        System.out.println("[DEBUG] Saving to Local DB...");
        userRepository.save(entity);
        System.out.println("[DEBUG] Saved to Local DB successfully");

        return mapToDto(entity);
    }

    @Override
    public UserResponseDto getCurrentUser(String username) {
        UserEntity entity = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found in local DB: " + username));
        return mapToDto(entity);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private UserResponseDto mapToDto(UserEntity entity) {
        return UserResponseDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .roles(Collections.singleton(entity.getRole()))
                .departmentId(entity.getDepartmentId())
                .build();
    }
}