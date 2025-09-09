package com.madaproject.Medikit.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attachments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attachmentId;

    @NotBlank(message = "Le nom du fichier est obligatoire")
    private String filename;

    @NotBlank(message = "Le type de fichier est obligatoire")
    private String fileType; // PDF, JPG, DICOM, etc.

    private Long fileSize;
    private String description;

    @NotBlank(message = "Le chemin de stockage est obligatoire")
    private String storagePath; // Chemin dans le système de fichiers ou URL S3

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
