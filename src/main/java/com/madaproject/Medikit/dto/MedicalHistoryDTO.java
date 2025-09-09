package com.madaproject.Medikit.dto;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MedicalHistoryDTO {
    private Long historyId;
    private String type;
    private String description;
    private LocalDate diagnosisDate;
    private String severity;
    private String status;
    private Long patientId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
