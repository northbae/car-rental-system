package kz.bmstu.kritinina.service.impl;

import kz.bmstu.kritinina.dto.PartnerDto;
import kz.bmstu.kritinina.exception.NotFoundException;
import kz.bmstu.kritinina.model.entity.Partner;
import kz.bmstu.kritinina.repository.PartnerRepository;
import kz.bmstu.kritinina.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;

    @Override
    public List<PartnerDto> getPartners() {
        return partnerRepository.findAll().stream()
                .map(p -> new PartnerDto(p.getId(), p.getName(), p.isDisabled()))
                .collect(Collectors.toList());
    }

    @Override
    public PartnerDto addPartner(PartnerDto partnerDto) {
        Partner partner = Partner.builder()
                .id(UUID.randomUUID())
                .name(partnerDto.getName())
                .disabled(partnerDto.isDisabled())
                .build();
        partner = partnerRepository.save(partner);
        return new PartnerDto(partner.getId(), partner.getName(), partner.isDisabled());
    }

    @Override
    public PartnerDto updatePartner(UUID id, PartnerDto partnerDto) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Partner not found"));
        partner.setName(partnerDto.getName());
        partner.setDisabled(partnerDto.isDisabled());
        partner = partnerRepository.save(partner);
        return new PartnerDto(partner.getId(), partner.getName(), partner.isDisabled());
    }

    @Override
    public void disablePartner(UUID id) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Partner not found"));
        partner.setDisabled(true);
        partnerRepository.save(partner);
    }
}