package kz.bmstu.kritinina.logging.service.impl;

import kz.bmstu.kritinina.logging.dto.LogEvent;
import kz.bmstu.kritinina.logging.dto.LogResponseDto;
import kz.bmstu.kritinina.logging.model.LogEntity;
import kz.bmstu.kritinina.logging.repository.LogRepository;
import kz.bmstu.kritinina.logging.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    @Override
    @Transactional
    public void saveLog(LogEvent event) {
        java.time.LocalDateTime ts = event.getTimestamp() != null 
                ? java.time.LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(event.getTimestamp()), java.time.ZoneId.systemDefault())
                : java.time.LocalDateTime.now();

        LogEntity entity = LogEntity.builder()
                .timestamp(ts)
                .serviceName(event.getService())
                .level(event.getLevel())
                .message(event.getMessage())
                .build();
        logRepository.save(entity);
    }

    @Override
    public List<LogResponseDto> getLogs(String service, String level) {
        List<LogEntity> logs;
        if (service != null && level != null) {
            logs = logRepository.findAllByServiceNameAndLevel(service, level);
        } else if (service != null) {
            logs = logRepository.findAllByServiceName(service);
        } else if (level != null) {
            logs = logRepository.findAllByLevel(level);
        } else {
            logs = logRepository.findAll();
        }

        return logs.stream()
                .map(l -> LogResponseDto.builder()
                        .id(l.getId())
                        .timestamp(l.getTimestamp())
                        .service(l.getServiceName())
                        .level(l.getLevel())
                        .message(l.getMessage())
                        .build())
                .collect(Collectors.toList());
    }
}