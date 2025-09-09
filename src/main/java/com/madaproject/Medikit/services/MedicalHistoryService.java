package com.madaproject.Medikit.services;
import com.madaproject.Medikit.models.MedicalHistory;
import com.madaproject.Medikit.models.Patient;
import com.madaproject.Medikit.repository.MedicalHistoryRepository;
import com.madaproject.Medikit.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalHistoryService {
    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    private PatientRepository patientRepository;

    public List<MedicalHistory> getMedicalHistoriesByPatientId(Long patientId) {
        return medicalHistoryRepository.findByPatientPatientIdOrderByCreatedAtDesc(patientId);
    }

    public Optional<MedicalHistory> getMedicalHistoryById(Long id) {
        return medicalHistoryRepository.findById(id);
    }

    public MedicalHistory createMedicalHistory(MedicalHistory medicalHistory, Long patientId) {
        Optional<Patient> patient = patientRepository.findById(patientId);
        if (patient.isPresent()) {
            medicalHistory.setPatient(patient.get());
            return medicalHistoryRepository.save(medicalHistory);
        }
        throw new RuntimeException("Patient non trouvé avec l'ID: " + patientId);
    }

    public MedicalHistory updateMedicalHistory(Long id, MedicalHistory medicalHistoryDetails) {
        Optional<MedicalHistory> medicalHistory = medicalHistoryRepository.findById(id);
        if (medicalHistory.isPresent()) {
            MedicalHistory existingHistory = medicalHistory.get();
            existingHistory.setType(medicalHistoryDetails.getType());
            existingHistory.setDescription(medicalHistoryDetails.getDescription());
            existingHistory.setDiagnosisDate(medicalHistoryDetails.getDiagnosisDate());
            existingHistory.setSeverity(medicalHistoryDetails.getSeverity());
            existingHistory.setStatus(medicalHistoryDetails.getStatus());
            return medicalHistoryRepository.save(existingHistory);
        }
        throw new RuntimeException("Antécédent médical non trouvé avec l'ID: " + id);
    }

    public void deleteMedicalHistory(Long id) {
        medicalHistoryRepository.deleteById(id);
    }
}
