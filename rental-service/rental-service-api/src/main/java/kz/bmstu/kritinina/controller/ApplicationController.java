package kz.bmstu.kritinina.controller;

import kz.bmstu.kritinina.dto.ApplicationRequestDto;
import kz.bmstu.kritinina.dto.ApplicationResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/applications")
public interface ApplicationController {

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
