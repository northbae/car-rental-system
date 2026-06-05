package kz.bmstu.kritinina.statistics.kafka;

import kz.bmstu.kritinina.statistics.dto.StatEvent;
import kz.bmstu.kritinina.statistics.service.StatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatConsumer {

    private final StatService statService;

    @KafkaListener(topics = "api.stats.topic", groupId = "statistics-group")
    public void consume(StatEvent event) {
        log.info("Received stat event from Kafka: {}", event);
        statService.processEvent(event);
    }
}