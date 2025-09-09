package com.madaproject.Medikit.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AttachmentDTO {
    private Long attachmentId;
    private String filename;
    private String fileType;
    private Long fileSize;
    private String description;
    private String storagePath;
    private Long patientId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
