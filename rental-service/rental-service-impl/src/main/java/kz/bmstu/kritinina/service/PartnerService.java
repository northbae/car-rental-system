package kz.bmstu.kritinina.service;

import kz.bmstu.kritinina.dto.PartnerDto;

import java.util.List;
import java.util.UUID;

public interface PartnerService {
    List<PartnerDto> getPartners();
    PartnerDto addPartner(PartnerDto partnerDto);
    PartnerDto updatePartner(UUID id, PartnerDto partnerDto);
    void disablePartner(UUID id);
}