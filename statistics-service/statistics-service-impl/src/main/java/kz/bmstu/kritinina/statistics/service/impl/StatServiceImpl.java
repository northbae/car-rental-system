package kz.bmstu.kritinina.statistics.service.impl;

import kz.bmstu.kritinina.statistics.dto.StatEvent;
import kz.bmstu.kritinina.statistics.dto.StatResponseDto;
import kz.bmstu.kritinina.statistics.model.StatEntity;
import kz.bmstu.kritinina.statistics.repository.StatRepository;
import kz.bmstu.kritinina.statistics.service.StatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final StatRepository statRepository;

    @Override
    @Transactional
    public void processEvent(StatEvent event) {
        log.info("Processing stat event: {} {}", event.getMethod(), event.getEndpoint());
        StatEntity stat = statRepository.findByMethodAndEndpoint(event.getMethod(), event.getEndpoint())
                .orElse(StatEntity.builder()
                        .method(event.getMethod())
                        .endpoint(event.getEndpoint())
                        .callCount(0L)
                        .build());
        
        stat.setCallCount(stat.getCallCount() + 1);
        statRepository.save(stat);
    }

    @Override
    public List<StatResponseDto> getStatistics() {
        return statRepository.findAll().stream()
                .map(s -> StatResponseDto.builder()
                        .method(s.getMethod())
                        .endpoint(s.getEndpoint())
                        .callCount(s.getCallCount())
                        .build())
                .collect(Collectors.toList());
    }
}