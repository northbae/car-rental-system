package kz.bmstu.kritinina.controller;

import kz.bmstu.kritinina.dto.QuotaDto;
import kz.bmstu.kritinina.dto.DepartmentStatisticsDto;
import kz.bmstu.kritinina.service.GatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DepartmentControllerImpl implements DepartmentController {

    private final GatewayService gatewayService;

    @Override
    public ResponseEntity<QuotaDto> getDepartmentQuotas(UUID id) {
        return ResponseEntity.ok(gatewayService.getDepartmentQuotas(id));
    }

    @Override
    public ResponseEntity<QuotaDto> updateDepartmentQuotas(UUID id, QuotaDto quotaDto) {
        return ResponseEntity.ok(gatewayService.updateDepartmentQuotas(id, quotaDto));
    }

    @Override
    public ResponseEntity<DepartmentStatisticsDto> getDepartmentStatistics(UUID id) {
        return ResponseEntity.ok(gatewayService.getDepartmentStatistics(id));
    }
}
