package com.madaproject.Medikit.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PrescriptionDTO {
    private Long prescriptionId;
    private String medication;
    private String dosage;
    private String frequency;
    private LocalDate startDate;
    private LocalDate endDate;
    private String instructions;
    private boolean isActive;
    private Long consultationId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
