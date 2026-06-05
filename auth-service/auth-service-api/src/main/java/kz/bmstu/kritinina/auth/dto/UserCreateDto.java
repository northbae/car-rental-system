package kz.bmstu.kritinina.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String role; // EMPLOYEE, MANAGER, ADMIN
    private String departmentId;
}