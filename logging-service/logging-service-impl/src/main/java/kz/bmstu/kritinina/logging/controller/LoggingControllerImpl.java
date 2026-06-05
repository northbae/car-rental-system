package kz.bmstu.kritinina.logging.controller;

import kz.bmstu.kritinina.logging.api.LoggingController;
import kz.bmstu.kritinina.logging.dto.LogResponseDto;
import kz.bmstu.kritinina.logging.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoggingControllerImpl implements LoggingController {

    private final LogService logService;

    @Override
    public ResponseEntity<List<LogResponseDto>> getLogs(String service, String level) {
        return ResponseEntity.ok(logService.getLogs(service, level));
    }
}