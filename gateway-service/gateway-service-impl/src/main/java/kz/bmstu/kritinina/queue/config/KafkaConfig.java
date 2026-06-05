package kz.bmstu.kritinina.queue.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    public static final String RETRY_TOPIC = "gateway.retry.topic";

    @Bean
    public NewTopic retryTopic() {
        return TopicBuilder.name(RETRY_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}