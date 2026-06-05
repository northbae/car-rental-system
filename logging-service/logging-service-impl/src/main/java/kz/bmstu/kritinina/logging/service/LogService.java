package kz.bmstu.kritinina.logging.service;

import kz.bmstu.kritinina.logging.dto.LogEvent;
import kz.bmstu.kritinina.logging.dto.LogResponseDto;

import java.util.List;

public interface LogService {
    void saveLog(LogEvent event);
    List<LogResponseDto> getLogs(String service, String level);
}