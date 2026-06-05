package kz.bmstu.kritinina.service;

import kz.bmstu.kritinina.dto.ApplicationRequestDto;
import kz.bmstu.kritinina.dto.ApplicationResponseDto;

import java.util.List;

public interface ApplicationService {
    ApplicationResponseDto createApplication(ApplicationRequestDto request, String username, String departmentId);
    List<ApplicationResponseDto> getApplications(String username);
    void cancelApplication(Long id, String username);
    ApplicationResponseDto approveApplication(Long id);
    ApplicationResponseDto rejectApplication(Long id, String comment);
    List<ApplicationResponseDto> getDepartmentApplications(String departmentId);
}