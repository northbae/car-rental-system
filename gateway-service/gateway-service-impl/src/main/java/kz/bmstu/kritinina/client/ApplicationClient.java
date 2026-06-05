package kz.bmstu.kritinina.client;

import kz.bmstu.kritinina.config.FeignConfig;
import kz.bmstu.kritinina.dto.ApplicationRequestDto;
import kz.bmstu.kritinina.dto.ApplicationResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "application", url = "http://rental-service:8060/api/v1/applications", configuration = FeignConfig.class)
public interface ApplicationClient {

    @PostMapping
    ResponseEntity<ApplicationResponseDto> createApplication(@RequestBody ApplicationRequestDto request);

    @GetMapping
    ResponseEntity<List<ApplicationResponseDto>> getApplications();

    @DeleteMapping("/{id}")
    ResponseEntity<Void> cancelApplication(@PathVariable("id") Long id);

    @PostMapping("/{id}/approve")
    ResponseEntity<ApplicationResponseDto> approveApplication(@PathVariable("id") Long id);

    @PostMapping("/{id}/reject")
    ResponseEntity<ApplicationResponseDto> rejectApplication(@PathVariable("id") Long id, @RequestParam("comment") String comment);

    @GetMapping("/department")
    ResponseEntity<List<ApplicationResponseDto>> getDepartmentApplications();
}
