package kz.bmstu.kritinina.config;

import kz.bmstu.kritinina.queue.config.KafkaConfig;
import kz.bmstu.kritinina.statistics.dto.StatEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatFilter extends OncePerRequestFilter {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } finally {
            String path = request.getRequestURI();
            String method = request.getMethod();

            // Skip static resources and actuator if needed
            if (!path.startsWith("/actuator") && !path.startsWith("/api/doc")) {
                StatEvent event = StatEvent.builder()
                        .method(method)
                        .endpoint(path)
                        .build();

                log.info("Sending stat event to Kafka: {} {}", method, path);
                kafkaTemplate.send("api.stats.topic", event);
            }
        }
    }
}