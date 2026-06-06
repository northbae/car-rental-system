package kz.bmstu.kritinina.queue.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    public static final String RETRY_TOPIC = "gateway.retry.topic";

}