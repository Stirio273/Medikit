package com.madaproject.Medikit.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ConsultationResponseDTO {
    private Long consultationId;
    private Long doctorId;
    private String doctorName;
    private LocalDateTime consultationDate;
    private String reason;
    private String observations;
    private String diagnosis;
    private String treatmentPlan;
    private Long patientId;
    private String patientName;
    private List<PrescriptionDTO> prescriptions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}