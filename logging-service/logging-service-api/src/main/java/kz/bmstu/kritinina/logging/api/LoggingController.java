package kz.bmstu.kritinina.logging.api;

import kz.bmstu.kritinina.logging.dto.LogResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/v1/logs")
public interface LoggingController {

    @GetMapping
    ResponseEntity<List<LogResponseDto>> getLogs(
            @RequestParam(value = "service", required = false) String service,
            @RequestParam(value = "level", required = false) String level
    );
}