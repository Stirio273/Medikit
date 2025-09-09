package com.madaproject.Medikit.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "medical_histories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @NotBlank(message = "Le type d'antécédent est obligatoire")
    private String type; // Antécédent familial, personnel, allergie, etc.

    @NotBlank(message = "La description est obligatoire")
    @Column(length = 1000)
    private String description;

    private LocalDate diagnosisDate;
    private String severity; // Léger, modéré, sévère
    private String status; // Actif, résolu, chronique

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
