package com.madaproject.Medikit.controllers;
import com.madaproject.Medikit.models.MedicalHistory;
import com.madaproject.Medikit.services.MedicalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients/{patientId}/medical-history")
public class MedicalHistoryController {
    @Autowired
    private MedicalHistoryService medicalHistoryService;

    @GetMapping
    public List<MedicalHistory> getPatientMedicalHistory(@PathVariable Long patientId) {
        return medicalHistoryService.getMedicalHistoriesByPatientId(patientId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalHistory> getMedicalHistoryById(@PathVariable Long id) {
        Optional<MedicalHistory> medicalHistory = medicalHistoryService.getMedicalHistoryById(id);
        return medicalHistory.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public MedicalHistory createMedicalHistory(@PathVariable Long patientId, 
                                              @RequestBody MedicalHistory medicalHistory) {
        return medicalHistoryService.createMedicalHistory(medicalHistory, patientId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalHistory> updateMedicalHistory(@PathVariable Long id, 
                                                              @RequestBody MedicalHistory medicalHistoryDetails) {
        try {
            MedicalHistory updatedHistory = medicalHistoryService.updateMedicalHistory(id, medicalHistoryDetails);
            return ResponseEntity.ok(updatedHistory);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMedicalHistory(@PathVariable Long id) {
        medicalHistoryService.deleteMedicalHistory(id);
        return ResponseEntity.ok().build();
    }
}
