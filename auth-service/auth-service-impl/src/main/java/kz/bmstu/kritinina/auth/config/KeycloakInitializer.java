package kz.bmstu.kritinina.auth.config;

import kz.bmstu.kritinina.auth.model.UserEntity;
import kz.bmstu.kritinina.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KeycloakInitializer {

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

    @Bean
    public CommandLineRunner initKeycloakData() {
        return args -> {
            try {
                Keycloak keycloak = getKeycloakInstance();
                
                // 1. Init Roles
                List<String> requiredRoles = Arrays.asList("EMPLOYEE", "MANAGER", "ADMIN");
                for (String roleName : requiredRoles) {
                    try {
                        keycloak.realm(realm).roles().get(roleName).toRepresentation();
                    } catch (Exception e) {
                        log.info("Creating role {}", roleName);
                        RoleRepresentation role = new RoleRepresentation();
                        role.setName(roleName);
                        keycloak.realm(realm).roles().create(role);
                    }
                }

                // 2. Create Default Users
                createUserIfNotExist(keycloak, "admin", "admin", "ADMIN", "IT");
                createUserIfNotExist(keycloak, "manager", "manager", "MANAGER", "IT");
                createUserIfNotExist(keycloak, "employee", "employee", "EMPLOYEE", "IT");

            } catch (Exception e) {
                log.error("Failed to initialize Keycloak: {}", e.getMessage());
            }
        };
    }

    private void createUserIfNotExist(Keycloak keycloak, String username, String password, String role, String dept) {
        List<UserRepresentation> search = keycloak.realm(realm).users().search(username);
        if (!search.isEmpty()) {
            log.info("User {} already exists", username);
            return;
        }

        log.info("Creating predefined user: {}", username);
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(username);
        user.setEmail(username + "@example.com");

        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setType(CredentialRepresentation.PASSWORD);
        cred.setValue(password);
        cred.setTemporary(false);
        user.setCredentials(Collections.singletonList(cred));

        Response response = keycloak.realm(realm).users().create(user);
        if (response.getStatus() == 201) {
            String userId = keycloak.realm(realm).users().search(username).get(0).getId();
            
            // Assign Role
            RoleRepresentation roleRep = keycloak.realm(realm).roles().get(role).toRepresentation();
            keycloak.realm(realm).users().get(userId).roles().realmLevel().add(Collections.singletonList(roleRep));

            // Save to local DB
            UserEntity entity = UserEntity.builder()
                    .id(UUID.fromString(userId))
                    .username(username)
                    .email(user.getEmail())
                    .role(role)
                    .departmentId(dept)
                    .build();
            userRepository.save(entity);
            log.info("User {} created successfully", username);
        } else {
            log.error("Failed to create user {}: status {}", username, response.getStatus());
        }
    }
}