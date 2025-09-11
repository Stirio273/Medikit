package com.madaproject.Medikit.services;
import com.madaproject.Medikit.models.Consultation;
import com.madaproject.Medikit.models.Prescription;
import com.madaproject.Medikit.repository.ConsultationRepository;
import com.madaproject.Medikit.repository.PrescriptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService {
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    public List<Prescription> getPrescriptionsByConsultationId(Long consultationId) {
        return prescriptionRepository.findByConsultationConsultationIdOrderByCreatedAtDesc(consultationId);
    }

    public Optional<Prescription> getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id);
    }

    public Prescription createPrescription(Prescription prescription, Long consultationId) {
        Optional<Consultation> consultation = consultationRepository.findById(consultationId);
        if (consultation.isPresent()) {
            prescription.setConsultation(consultation.get());
            return prescriptionRepository.save(prescription);
        }
        throw new RuntimeException("Consultation non trouvée avec l'ID: " + consultationId);
    }

    public Prescription updatePrescription(Long id, Prescription prescriptionDetails) {
        Optional<Prescription> prescription = prescriptionRepository.findById(id);
        if (prescription.isPresent()) {
            Prescription existingPrescription = prescription.get();
            existingPrescription.setMedication(prescriptionDetails.getMedication());
            existingPrescription.setDosage(prescriptionDetails.getDosage());
            existingPrescription.setFrequency(prescriptionDetails.getFrequency());
            existingPrescription.setStartDate(prescriptionDetails.getStartDate());
            existingPrescription.setEndDate(prescriptionDetails.getEndDate());
            existingPrescription.setInstructions(prescriptionDetails.getInstructions());
            existingPrescription.setActive(prescriptionDetails.isActive());
            return prescriptionRepository.save(existingPrescription);
        }
        throw new RuntimeException("Prescription non trouvée avec l'ID: " + id);
    }

    public void deletePrescription(Long id) {
        prescriptionRepository.deleteById(id);
    }
}

