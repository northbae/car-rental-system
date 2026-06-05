package kz.bmstu.kritinina.analytics.api;

import kz.bmstu.kritinina.analytics.dto.AnalyticsResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/analytics")
public interface AnalyticsController {

    @GetMapping
    ResponseEntity<AnalyticsResponseDto> getAnalytics();
}