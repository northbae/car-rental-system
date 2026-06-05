package kz.bmstu.kritinina.statistics.controller;

import kz.bmstu.kritinina.statistics.api.StatisticsController;
import kz.bmstu.kritinina.statistics.dto.StatResponseDto;
import kz.bmstu.kritinina.statistics.service.StatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatisticsControllerImpl implements StatisticsController {

    private final StatService statService;

    @Override
    public ResponseEntity<List<StatResponseDto>> getStatistics() {
        return ResponseEntity.ok(statService.getStatistics());
    }
}