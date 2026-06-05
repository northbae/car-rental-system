package kz.bmstu.kritinina.controller;

import kz.bmstu.kritinina.dto.PartnerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/partners")
public interface PartnerController {

    @GetMapping
    ResponseEntity<List<PartnerDto>> getPartners();

    @PostMapping
    ResponseEntity<PartnerDto> addPartner(@RequestBody PartnerDto partnerDto);

    @PutMapping("/{id}")
    ResponseEntity<PartnerDto> updatePartner(@PathVariable("id") UUID id, @RequestBody PartnerDto partnerDto);

    @PostMapping("/{id}/disable")
    ResponseEntity<Void> disablePartner(@PathVariable("id") UUID id);
}
