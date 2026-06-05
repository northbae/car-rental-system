package kz.bmstu.kritinina.statistics.service;

import kz.bmstu.kritinina.statistics.dto.StatEvent;
import kz.bmstu.kritinina.statistics.dto.StatResponseDto;

import java.util.List;

public interface StatService {
    void processEvent(StatEvent event);
    List<StatResponseDto> getStatistics();
}