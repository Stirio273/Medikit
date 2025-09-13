package com.madaproject.Medikit.controllers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.madaproject.Medikit.dto.PatientDTO;
import com.madaproject.Medikit.services.PatientService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@Validated @RequestBody PatientDTO patientDTO) {
        // return ResponseEntity.ok(patientService.createPatient(patientDTO));
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(patientService.createPatient(patientDTO));   
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Long id, @Validated @RequestBody PatientDTO patientDTO) {
        return ResponseEntity.ok(patientService.updatePatient(id, patientDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<PatientDTO>> searchPatients(@RequestParam String keyword) {
        return ResponseEntity.ok(patientService.searchPatients(keyword));
    }

    // Recherche avancée avec plusieurs paramètres
    @GetMapping("/advanced-search")
    public ResponseEntity<List<PatientDTO>> advancedSearch(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String prenom,
            @RequestParam(required = false) String email) {
        
        // Si un ID est spécifié, on le priorise
        if (id != null) {
            try {
                PatientDTO patient = patientService.getPatientById(id);
                return ResponseEntity.ok(List.of(patient));
            } catch (RuntimeException e) {
                return ResponseEntity.ok(List.of());
            }
        }
        
        // Sinon, on recherche par les autres critères
        List<PatientDTO> results = patientService.getAllPatients();
        
        if (nom != null && !nom.trim().isEmpty()) {
            results = results.stream()
                    .filter(p -> p.getNom().toLowerCase().contains(nom.toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        if (prenom != null && !prenom.trim().isEmpty()) {
            results = results.stream()
                    .filter(p -> p.getPrenom().toLowerCase().contains(prenom.toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        if (email != null && !email.trim().isEmpty()) {
            results = results.stream()
                    .filter(p -> p.getEmail().toLowerCase().contains(email.toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        return ResponseEntity.ok(results);
    }
}
