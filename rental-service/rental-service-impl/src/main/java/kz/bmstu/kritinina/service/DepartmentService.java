package kz.bmstu.kritinina.service;

import kz.bmstu.kritinina.dto.QuotaDto;
import kz.bmstu.kritinina.dto.DepartmentStatisticsDto;

public interface DepartmentService {
    QuotaDto getDepartmentQuotas(String departmentId);
    QuotaDto updateDepartmentQuotas(String departmentId, QuotaDto quotaDto);
    DepartmentStatisticsDto getDepartmentStatistics(String departmentId);
}