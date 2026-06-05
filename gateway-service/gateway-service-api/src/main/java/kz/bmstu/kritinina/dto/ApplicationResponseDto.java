package kz.bmstu.kritinina.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponseDto {
    private Long id;
    private UUID carUid;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String purpose;
    private String status;
    private String rejectionComment;
}
