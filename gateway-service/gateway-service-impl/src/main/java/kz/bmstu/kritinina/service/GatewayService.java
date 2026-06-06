package kz.bmstu.kritinina.service;

import kz.bmstu.kritinina.config.CarPage;
import kz.bmstu.kritinina.dto.BookCarDto;
import kz.bmstu.kritinina.dto.CarDto;
import kz.bmstu.kritinina.dto.RentalCreationDto;
import kz.bmstu.kritinina.dto.RentalDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface GatewayService {
    CarPage<CarDto> getAllCars(Boolean showAll, String dateFrom, String dateTo, String carType, String city, Pageable pageable);
    List<RentalDto> getRental();
    RentalDto getRental(UUID rentalUid);
    RentalCreationDto bookCar(BookCarDto bookCarDto);
    void finishRental(UUID rentalUid);
    void cancelRental(UUID rentalUid);
    
    kz.bmstu.kritinina.dto.ApplicationResponseDto createApplication(kz.bmstu.kritinina.dto.ApplicationRequestDto request);
    List<kz.bmstu.kritinina.dto.ApplicationResponseDto> getApplications();
    void cancelApplication(Long id);
    kz.bmstu.kritinina.dto.ApplicationResponseDto approveApplication(Long id);
    kz.bmstu.kritinina.dto.ApplicationResponseDto rejectApplication(Long id, String comment);
    List<kz.bmstu.kritinina.dto.ApplicationResponseDto> getDepartmentApplications();

    List<kz.bmstu.kritinina.dto.PartnerDto> getPartners();
    kz.bmstu.kritinina.dto.PartnerDto addPartner(kz.bmstu.kritinina.dto.PartnerDto partnerDto);
    kz.bmstu.kritinina.dto.PartnerDto updatePartner(UUID id, kz.bmstu.kritinina.dto.PartnerDto partnerDto);
    void disablePartner(UUID id);

    kz.bmstu.kritinina.dto.QuotaDto getDepartmentQuotas(UUID id);
    kz.bmstu.kritinina.dto.QuotaDto updateDepartmentQuotas(UUID id, kz.bmstu.kritinina.dto.QuotaDto quotaDto);
    kz.bmstu.kritinina.dto.DepartmentStatisticsDto getDepartmentStatistics(UUID id);

    kz.bmstu.kritinina.auth.dto.UserResponseDto createUser(kz.bmstu.kritinina.auth.dto.UserCreateDto dto);
    kz.bmstu.kritinina.auth.dto.UserResponseDto getCurrentUser();
    List<kz.bmstu.kritinina.auth.dto.UserResponseDto> getAllUsers();
}