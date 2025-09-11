package com.madaproject.Medikit.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "consultations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consultationId;

    @NotNull(message = "Le médecin est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User doctor;

    private LocalDateTime consultationDate;
    
    @NotBlank(message = "Le motif de consultation est obligatoire")
    private String reason;

    @Column(length = 2000)
    private String observations;

    private String diagnosis;
    
    @Column(length = 2000)
    private String treatmentPlan;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prescription> prescriptions = new ArrayList<>();

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
