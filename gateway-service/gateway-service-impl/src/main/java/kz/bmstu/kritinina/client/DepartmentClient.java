package kz.bmstu.kritinina.client;

import kz.bmstu.kritinina.config.FeignConfig;
import kz.bmstu.kritinina.dto.QuotaDto;
import kz.bmstu.kritinina.dto.DepartmentStatisticsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "department", url = "http://rental-service:8060/api/v1/departments", configuration = FeignConfig.class)
public interface DepartmentClient {

    @GetMapping("/{id}/quotas")
    ResponseEntity<QuotaDto> getDepartmentQuotas(@PathVariable("id") UUID id);

    @PutMapping("/{id}/quotas")
    ResponseEntity<QuotaDto> updateDepartmentQuotas(@PathVariable("id") UUID id, @RequestBody QuotaDto quotaDto);

    @GetMapping("/{id}/statistics")
    ResponseEntity<DepartmentStatisticsDto> getDepartmentStatistics(@PathVariable("id") UUID id);
}