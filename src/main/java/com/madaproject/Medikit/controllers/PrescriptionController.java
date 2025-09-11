package com.madaproject.Medikit.controllers;
import com.madaproject.Medikit.models.Prescription;
import com.madaproject.Medikit.services.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/consultations/{consultationId}/prescriptions")
public class PrescriptionController {
    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping
    public List<Prescription> getConsultationPrescriptions(@PathVariable Long consultationId) {
        return prescriptionService.getPrescriptionsByConsultationId(consultationId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable Long id) {
        Optional<Prescription> prescription = prescriptionService.getPrescriptionById(id);
        return prescription.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Prescription createPrescription(@PathVariable Long consultationId, 
                                          @RequestBody Prescription prescription) {
        return prescriptionService.createPrescription(prescription, consultationId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prescription> updatePrescription(@PathVariable Long id, 
                                                          @RequestBody Prescription prescriptionDetails) {
        try {
            Prescription updatedPrescription = prescriptionService.updatePrescription(id, prescriptionDetails);
            return ResponseEntity.ok(updatedPrescription);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return ResponseEntity.ok().build();
    }
}
