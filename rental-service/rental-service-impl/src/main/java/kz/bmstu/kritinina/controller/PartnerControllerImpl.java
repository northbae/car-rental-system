package kz.bmstu.kritinina.controller;

import kz.bmstu.kritinina.dto.PartnerDto;
import kz.bmstu.kritinina.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PartnerControllerImpl implements PartnerController {

    private final PartnerService partnerService;

    @Override
    public ResponseEntity<List<PartnerDto>> getPartners() {
        return ResponseEntity.ok(partnerService.getPartners());
    }

    @Override
    public ResponseEntity<PartnerDto> addPartner(PartnerDto partnerDto) {
        return ResponseEntity.ok(partnerService.addPartner(partnerDto));
    }

    @Override
    public ResponseEntity<PartnerDto> updatePartner(UUID id, PartnerDto partnerDto) {
        return ResponseEntity.ok(partnerService.updatePartner(id, partnerDto));
    }

    @Override
    public ResponseEntity<Void> disablePartner(UUID id) {
        partnerService.disablePartner(id);
        return ResponseEntity.ok().build();
    }
}
