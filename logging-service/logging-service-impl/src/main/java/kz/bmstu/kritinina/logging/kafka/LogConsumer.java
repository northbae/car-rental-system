package kz.bmstu.kritinina.logging.kafka;

import kz.bmstu.kritinina.logging.dto.LogEvent;
import kz.bmstu.kritinina.logging.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogConsumer {

    private final LogService logService;

    @KafkaListener(topics = "api.logs.topic", groupId = "logging-group")
    public void consume(LogEvent event) {
        log.info("Received log event from Kafka: {} [{}] {}", event.getService(), event.getLevel(), event.getMessage());
        logService.saveLog(event);
    }
}