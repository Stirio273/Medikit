package com.madaproject.Medikit.controllers;
import com.madaproject.Medikit.models.Attachment;
import com.madaproject.Medikit.models.Patient;
import com.madaproject.Medikit.repository.AttachmentRepository;
import com.madaproject.Medikit.repository.PatientRepository;
import com.madaproject.Medikit.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients/{patientId}/attachments")
public class AttachmentController {
    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping
    public List<Attachment> getPatientAttachments(@PathVariable Long patientId) {
        return attachmentRepository.findByPatientPatientIdOrderByCreatedAtDesc(patientId);
    }

    @PostMapping
    public Attachment uploadAttachment(@PathVariable Long patientId,
                                      @RequestParam("file") MultipartFile file,
                                      @RequestParam("description") String description) {
        Optional<Patient> patient = patientRepository.findById(patientId);
        if (patient.isEmpty()) {
            throw new RuntimeException("Patient non trouvé avec l'ID: " + patientId);
        }

        // Stocker le fichier
        String storedFilename = fileStorageService.storeFile(file);

        // Créer l'entrée en base de données
        Attachment attachment = new Attachment();
        attachment.setFilename(file.getOriginalFilename());
        attachment.setFileType(file.getContentType());
        attachment.setFileSize(file.getSize());
        attachment.setDescription(description);
        attachment.setStoragePath(storedFilename);
        attachment.setPatient(patient.get());

        return attachmentRepository.save(attachment);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadAttachment(@PathVariable Long id) {
        Optional<Attachment> attachment = attachmentRepository.findById(id);
        if (attachment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            Path filePath = Paths.get(uploadDir).resolve(attachment.get().getStoragePath()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(attachment.get().getFileType()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, 
                                "attachment; filename=\"" + attachment.get().getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAttachment(@PathVariable Long id) {
        attachmentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
