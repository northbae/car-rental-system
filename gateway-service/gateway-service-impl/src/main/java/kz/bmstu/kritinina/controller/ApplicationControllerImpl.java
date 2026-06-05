package kz.bmstu.kritinina.controller;

import kz.bmstu.kritinina.dto.ApplicationRequestDto;
import kz.bmstu.kritinina.dto.ApplicationResponseDto;
import kz.bmstu.kritinina.service.GatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationControllerImpl implements ApplicationController {

    private final GatewayService gatewayService;

    @Override
    public ResponseEntity<ApplicationResponseDto> createApplication(ApplicationRequestDto request) {
        return ResponseEntity.ok(gatewayService.createApplication(request));
    }

    @Override
    public ResponseEntity<List<ApplicationResponseDto>> getApplications() {
        return ResponseEntity.ok(gatewayService.getApplications());
    }

    @Override
    public ResponseEntity<Void> cancelApplication(Long id) {
        gatewayService.cancelApplication(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ApplicationResponseDto> approveApplication(Long id) {
        return ResponseEntity.ok(gatewayService.approveApplication(id));
    }

    @Override
    public ResponseEntity<ApplicationResponseDto> rejectApplication(Long id, String comment) {
        return ResponseEntity.ok(gatewayService.rejectApplication(id, comment));
    }

    @Override
    public ResponseEntity<List<ApplicationResponseDto>> getDepartmentApplications() {
        return ResponseEntity.ok(gatewayService.getDepartmentApplications());
    }
}
