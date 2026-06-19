package kz.bmstu.kritinina.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.time.LocalDateTime;

public class KafkaLogAppender extends AppenderBase<ILoggingEvent> {

    private String bootstrapServers;
    private String topic;
    private String serviceName;
    private KafkaProducer<String, String> producer;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public void start() {
        if (bootstrapServers == null || topic == null || serviceName == null) {
            System.err.println("KafkaLogAppender properties not set");
            return;
        }

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 100);

        try {
            // Lazy initialization to avoid crashing Spring Boot during Logback startup
            super.start();
        } catch (Exception e) {
            System.err.println("Failed to start KafkaLogAppender: " + e.getMessage());
        }
    }

    @Override
    protected void append(ILoggingEvent event) {
        if (!isStarted() || event.getLoggerName().contains("org.apache.kafka") || event.getLoggerName().contains("kz.bmstu.kritinina.logging")) {
            return;
        }

        if (producer == null) {
            try {
                Properties props = new Properties();
                props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
                props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
                props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
                props.put(ProducerConfig.RETRIES_CONFIG, 0);
                props.put(ProducerConfig.LINGER_MS_CONFIG, 100);
                producer = new KafkaProducer<>(props);
            } catch (Exception e) {
                return; // Fail silently if Kafka is still down
            }
        }

        try {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put("service", serviceName);
            logMap.put("level", event.getLevel().toString());
            logMap.put("message", event.getFormattedMessage());
            logMap.put("timestamp", System.currentTimeMillis());

            String json = objectMapper.writeValueAsString(logMap);
            producer.send(new ProducerRecord<>(topic, json));
        } catch (Exception ignored) {
        }
    }

    @Override
    public void stop() {
        if (producer != null) {
            producer.close();
        }
        super.stop();
    }

    public void setBootstrapServers(String bootstrapServers) { this.bootstrapServers = bootstrapServers; }
    public void setTopic(String topic) { this.topic = topic; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
}