package kz.bmstu.kritinina.queue.producer;

import kz.bmstu.kritinina.queue.config.KafkaConfig;
import kz.bmstu.kritinina.queue.message.RetryMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class RetryProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendToRetryQueue(String operationType, Map<String, Object> payload) {

        RetryMessage message = RetryMessage.builder()
                .operationType(operationType)
                .payload(payload)
                .createdAt(LocalDateTime.now())
                .build();

        log.info("Sending message to Kafka topic {}: {}", KafkaConfig.RETRY_TOPIC, message);
        kafkaTemplate.send(KafkaConfig.RETRY_TOPIC, message);
    }
}