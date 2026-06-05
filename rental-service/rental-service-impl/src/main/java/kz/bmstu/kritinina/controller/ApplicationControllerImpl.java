package kz.bmstu.kritinina.controller;

import kz.bmstu.kritinina.dto.ApplicationRequestDto;
import kz.bmstu.kritinina.dto.ApplicationResponseDto;
import kz.bmstu.kritinina.service.ApplicationService;
import kz.bmstu.kritinina.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationControllerImpl implements ApplicationController {

    private final ApplicationService applicationService;
    private final JwtUtils jwtUtils;

    @Override
    public ResponseEntity<ApplicationResponseDto> createApplication(ApplicationRequestDto request) {
        String username = jwtUtils.getCurrentUsername();
        // Defaulting to "IT" if not specified. In a real scenario, this comes from token claims.
        String departmentId = "IT"; 
        return ResponseEntity.ok(applicationService.createApplication(request, username, departmentId));
    }

    @Override
    public ResponseEntity<List<ApplicationResponseDto>> getApplications() {
        return ResponseEntity.ok(applicationService.getApplications(jwtUtils.getCurrentUsername()));
    }

    @Override
    public ResponseEntity<Void> cancelApplication(Long id) {
        applicationService.cancelApplication(id, jwtUtils.getCurrentUsername());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<ApplicationResponseDto> approveApplication(Long id) {
        return ResponseEntity.ok(applicationService.approveApplication(id));
    }

    @Override
    public ResponseEntity<ApplicationResponseDto> rejectApplication(Long id, String comment) {
        return ResponseEntity.ok(applicationService.rejectApplication(id, comment));
    }

    @Override
    public ResponseEntity<List<ApplicationResponseDto>> getDepartmentApplications() {
        return ResponseEntity.ok(applicationService.getDepartmentApplications("IT"));
    }
}
