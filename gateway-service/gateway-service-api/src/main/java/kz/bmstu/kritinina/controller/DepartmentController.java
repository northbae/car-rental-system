package kz.bmstu.kritinina.controller;

import kz.bmstu.kritinina.dto.QuotaDto;
import kz.bmstu.kritinina.dto.DepartmentStatisticsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/departments")
public interface DepartmentController {

    @GetMapping("/{id}/quotas")
    ResponseEntity<QuotaDto> getDepartmentQuotas(@PathVariable("id") UUID id);

    @PutMapping("/{id}/quotas")
    ResponseEntity<QuotaDto> updateDepartmentQuotas(@PathVariable("id") UUID id, @RequestBody QuotaDto quotaDto);

    @GetMapping("/{id}/statistics")
    ResponseEntity<DepartmentStatisticsDto> getDepartmentStatistics(@PathVariable("id") UUID id);
}
