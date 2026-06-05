package kz.bmstu.kritinina.service.impl;

import kz.bmstu.kritinina.dto.ApplicationRequestDto;
import kz.bmstu.kritinina.dto.ApplicationResponseDto;
import kz.bmstu.kritinina.exception.InvalidOperationException;
import kz.bmstu.kritinina.exception.NotFoundException;
import kz.bmstu.kritinina.model.entity.ApplicationRequest;
import kz.bmstu.kritinina.model.entity.DepartmentQuota;
import kz.bmstu.kritinina.model.enums.ApplicationStatus;
import kz.bmstu.kritinina.repository.ApplicationRequestRepository;
import kz.bmstu.kritinina.repository.DepartmentQuotaRepository;
import kz.bmstu.kritinina.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRequestRepository applicationRepository;
    private final DepartmentQuotaRepository quotaRepository;

    @Override
    @Transactional
    public ApplicationResponseDto createApplication(ApplicationRequestDto request, String username, String departmentId) {
        DepartmentQuota quota = quotaRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException("Quota for department not found"));

        if (quota.getUsedQuota() >= quota.getTotalQuota()) {
            throw new InvalidOperationException("Department quota exceeded");
        }

        ApplicationRequest app = ApplicationRequest.builder()
                .applicationUid(UUID.randomUUID())
                .username(username)
                .departmentId(departmentId)
                .carUid(request.getCarUid())
                .dateFrom(request.getDateFrom())
                .dateTo(request.getDateTo())
                .purpose(request.getPurpose())
                .status(ApplicationStatus.PENDING_APPROVAL)
                .build();

        app = applicationRepository.save(app);
        return mapToDto(app);
    }

    @Override
    public List<ApplicationResponseDto> getApplications(String username) {
        return applicationRepository.findAllByUsername(username).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelApplication(Long id, String username) {
        ApplicationRequest app = applicationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Application not found"));

        if (!app.getUsername().equals(username)) {
            throw new InvalidOperationException("Not allowed to cancel this application");
        }

        if (app.getStatus() != ApplicationStatus.PENDING_APPROVAL) {
            throw new InvalidOperationException("Can only cancel pending applications");
        }

        app.setStatus(ApplicationStatus.CANCELLED);
        applicationRepository.save(app);
    }

    @Override
    @Transactional
    public ApplicationResponseDto approveApplication(Long id) {
        ApplicationRequest app = applicationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Application not found"));

        if (app.getStatus() != ApplicationStatus.PENDING_APPROVAL) {
            throw new InvalidOperationException("Application is not pending");
        }

        DepartmentQuota quota = quotaRepository.findById(app.getDepartmentId())
                .orElseThrow(() -> new NotFoundException("Quota for department not found"));

        if (quota.getUsedQuota() >= quota.getTotalQuota()) {
            throw new InvalidOperationException("Department quota exceeded");
        }

        app.setStatus(ApplicationStatus.APPROVED);
        applicationRepository.save(app);

        quota.setUsedQuota(quota.getUsedQuota() + 1);
        quotaRepository.save(quota);

        return mapToDto(app);
    }

    @Override
    public ApplicationResponseDto rejectApplication(Long id, String comment) {
        ApplicationRequest app = applicationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Application not found"));

        if (app.getStatus() != ApplicationStatus.PENDING_APPROVAL) {
            throw new InvalidOperationException("Application is not pending");
        }

        app.setStatus(ApplicationStatus.REJECTED);
        app.setComment(comment);
        applicationRepository.save(app);

        return mapToDto(app);
    }

    @Override
    public List<ApplicationResponseDto> getDepartmentApplications(String departmentId) {
        return applicationRepository.findAllByDepartmentId(departmentId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ApplicationResponseDto mapToDto(ApplicationRequest app) {
        return ApplicationResponseDto.builder()
                .id(app.getId())
                .carUid(app.getCarUid())
                .dateFrom(app.getDateFrom())
                .dateTo(app.getDateTo())
                .purpose(app.getPurpose())
                .status(app.getStatus().name())
                .rejectionComment(app.getComment())
                .build();
    }
}