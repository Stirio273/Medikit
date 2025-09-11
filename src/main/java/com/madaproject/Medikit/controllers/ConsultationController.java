package com.madaproject.Medikit.controllers;
import com.madaproject.Medikit.dto.ConsultationResponseDTO;
import com.madaproject.Medikit.models.Consultation;
import com.madaproject.Medikit.services.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients/{patientId}/consultations")
public class ConsultationController {
    @Autowired
    private ConsultationService consultationService;

    @GetMapping
    public List<ConsultationResponseDTO> getPatientConsultations(@PathVariable Long patientId) {
        return consultationService.getConsultationsByPatientId(patientId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultationResponseDTO> getConsultationById(@PathVariable Long id) {
        Optional<ConsultationResponseDTO> consultation = consultationService.getConsultationById(id);
        return consultation.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ConsultationResponseDTO> createConsultation(
            @PathVariable Long patientId, 
            @RequestBody Consultation consultation,
            @RequestParam Long doctorId) {
        try {
            ConsultationResponseDTO createdConsultation = consultationService.createConsultation(consultation, patientId, doctorId);
            return ResponseEntity.ok(createdConsultation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultationResponseDTO> updateConsultation(
            @PathVariable Long id, 
            @RequestBody Consultation consultationDetails,
            @RequestParam Long doctorId) {
        try {
            ConsultationResponseDTO updatedConsultation = consultationService.updateConsultation(id, consultationDetails, doctorId);
            return ResponseEntity.ok(updatedConsultation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteConsultation(@PathVariable Long id) {
        consultationService.deleteConsultation(id);
        return ResponseEntity.ok().build();
    }
}
