package kz.bmstu.kritinina.auth.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KeycloakInitializer {

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
    public CommandLineRunner initRoles() {
        return args -> {
            try {
                Keycloak keycloak = getKeycloakInstance();
                List<String> requiredRoles = Arrays.asList("EMPLOYEE", "MANAGER", "ADMIN");

                for (String roleName : requiredRoles) {
                    try {
                        keycloak.realm(realm).roles().get(roleName).toRepresentation();
                        log.info("Role {} already exists", roleName);
                    } catch (Exception e) {
                        log.info("Creating role {}", roleName);
                        RoleRepresentation role = new RoleRepresentation();
                        role.setName(roleName);
                        keycloak.realm(realm).roles().create(role);
                    }
                }
            } catch (Exception e) {
                log.error("Failed to initialize Keycloak roles: {}", e.getMessage());
            }
        };
    }
}