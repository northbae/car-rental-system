package kz.bmstu.kritinina.logging.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogResponseDto {
    private Long id;
    private LocalDateTime timestamp;
    private String service;
    private String level;
    private String message;
}