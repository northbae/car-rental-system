package kz.bmstu.kritinina.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatResponseDto {
    private String method;
    private String endpoint;
    private Long callCount;
}