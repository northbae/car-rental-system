package kz.bmstu.kritinina.statistics.api;

import kz.bmstu.kritinina.statistics.dto.StatResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/v1/statistics")
public interface StatisticsController {

    @GetMapping
    ResponseEntity<List<StatResponseDto>> getStatistics();
}