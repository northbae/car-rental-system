package kz.bmstu.kritinina.service.impl;

import kz.bmstu.kritinina.dto.DepartmentStatisticsDto;
import kz.bmstu.kritinina.dto.QuotaDto;
import kz.bmstu.kritinina.exception.NotFoundException;
import kz.bmstu.kritinina.model.entity.DepartmentQuota;
import kz.bmstu.kritinina.repository.DepartmentQuotaRepository;
import kz.bmstu.kritinina.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentQuotaRepository quotaRepository;

    @Override
    public QuotaDto getDepartmentQuotas(String departmentId) {
        DepartmentQuota quota = quotaRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException("Department Quota not found"));
        return new QuotaDto(quota.getTotalQuota(), quota.getUsedQuota());
    }

    @Override
    public QuotaDto updateDepartmentQuotas(String departmentId, QuotaDto quotaDto) {
        DepartmentQuota quota = quotaRepository.findById(departmentId).orElse(
                DepartmentQuota.builder().departmentId(departmentId).usedQuota(0).build()
        );
        quota.setTotalQuota(quotaDto.getTotalQuota());
        quota = quotaRepository.save(quota);
        return new QuotaDto(quota.getTotalQuota(), quota.getUsedQuota());
    }

    @Override
    public DepartmentStatisticsDto getDepartmentStatistics(String departmentId) {
        DepartmentQuota quota = quotaRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException("Department Quota not found"));
        // Simulating UUID for departmentId, parsing from String or using default if parsing fails
        UUID depUuid;
        try {
            depUuid = UUID.fromString(departmentId);
        } catch (IllegalArgumentException e) {
            depUuid = UUID.randomUUID(); // Fallback for non-UUID strings like "IT"
        }
        return new DepartmentStatisticsDto(depUuid, quota.getTotalQuota(), quota.getUsedQuota());
    }
}