package kz.bmstu.kritinina.controller;

import kz.bmstu.kritinina.dto.PartnerDto;
import kz.bmstu.kritinina.service.GatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PartnerControllerImpl implements PartnerController {

    private final GatewayService gatewayService;

    @Override
    public ResponseEntity<List<PartnerDto>> getPartners() {
        return ResponseEntity.ok(gatewayService.getPartners());
    }

    @Override
    public ResponseEntity<PartnerDto> addPartner(PartnerDto partnerDto) {
        return ResponseEntity.ok(gatewayService.addPartner(partnerDto));
    }

    @Override
    public ResponseEntity<PartnerDto> updatePartner(UUID id, PartnerDto partnerDto) {
        return ResponseEntity.ok(gatewayService.updatePartner(id, partnerDto));
    }

    @Override
    public ResponseEntity<Void> disablePartner(UUID id) {
        gatewayService.disablePartner(id);
        return ResponseEntity.ok().build();
    }
}
