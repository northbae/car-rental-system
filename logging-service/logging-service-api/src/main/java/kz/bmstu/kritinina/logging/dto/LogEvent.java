package kz.bmstu.kritinina.logging.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogEvent implements Serializable {
    private String service;
    private String level;
    private String message;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}