package com.madaproject.Medikit.dto;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ConsultationDTO {
    private Long consultationId;
    private String doctor;
    private LocalDateTime consultationDate;
    private String reason;
    private String observations;
    private String diagnosis;
    private String treatmentPlan;
    private Long patientId;
    private List<PrescriptionDTO> prescriptions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
