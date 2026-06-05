package kz.bmstu.kritinina.client;

import kz.bmstu.kritinina.config.FeignConfig;
import kz.bmstu.kritinina.dto.PartnerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "partner", url = "http://rental-service:8060/api/v1/partners", configuration = FeignConfig.class)
public interface PartnerClient {

    @GetMapping
    ResponseEntity<List<PartnerDto>> getPartners();

    @PostMapping
    ResponseEntity<PartnerDto> addPartner(@RequestBody PartnerDto partnerDto);

    @PutMapping("/{id}")
    ResponseEntity<PartnerDto> updatePartner(@PathVariable("id") UUID id, @RequestBody PartnerDto partnerDto);

    @PostMapping("/{id}/disable")
    ResponseEntity<Void> disablePartner(@PathVariable("id") UUID id);
}