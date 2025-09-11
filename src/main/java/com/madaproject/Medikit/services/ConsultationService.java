package com.madaproject.Medikit.services;
import com.madaproject.Medikit.dto.ConsultationResponseDTO;
import com.madaproject.Medikit.mapper.ConsultationMapper;
import com.madaproject.Medikit.models.Consultation;
import com.madaproject.Medikit.models.Patient;
import com.madaproject.Medikit.models.User;
import com.madaproject.Medikit.repository.ConsultationRepository;
import com.madaproject.Medikit.repository.PatientRepository;
import com.madaproject.Medikit.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultationService {
    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConsultationMapper consultationMapper;

    public List<ConsultationResponseDTO> getConsultationsByPatientId(Long patientId) {
        List<Consultation> consultations = consultationRepository.findByPatientPatientIdOrderByConsultationDateDesc(patientId);
        return consultations.stream()
                .map(consultationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<ConsultationResponseDTO> getConsultationById(Long id) {
        Optional<Consultation> consultation = consultationRepository.findById(id);
        return consultation.map(consultationMapper::toResponseDTO);
    }

    public ConsultationResponseDTO createConsultation(Consultation consultation, Long patientId, Long doctorId) {
        Optional<Patient> patient = patientRepository.findById(patientId);
        Optional<User> doctor = userRepository.findById(doctorId);
        
        if (patient.isPresent() && doctor.isPresent()) {
            consultation.setPatient(patient.get());
            consultation.setDoctor(doctor.get());
            Consultation savedConsultation = consultationRepository.save(consultation);
            return consultationMapper.toResponseDTO(savedConsultation);
        }
        throw new RuntimeException("Patient ou médecin non trouvé");
    }

    public ConsultationResponseDTO updateConsultation(Long id, Consultation consultationDetails, Long doctorId) {
        Optional<Consultation> consultation = consultationRepository.findById(id);
        Optional<User> doctor = userRepository.findById(doctorId);
        
        if (consultation.isPresent() && doctor.isPresent()) {
            Consultation existingConsultation = consultation.get();
            existingConsultation.setDoctor(doctor.get());
            existingConsultation.setConsultationDate(consultationDetails.getConsultationDate());
            existingConsultation.setReason(consultationDetails.getReason());
            existingConsultation.setObservations(consultationDetails.getObservations());
            existingConsultation.setDiagnosis(consultationDetails.getDiagnosis());
            existingConsultation.setTreatmentPlan(consultationDetails.getTreatmentPlan());
            
            Consultation updatedConsultation = consultationRepository.save(existingConsultation);
            return consultationMapper.toResponseDTO(updatedConsultation);
        }
        throw new RuntimeException("Consultation ou médecin non trouvé");
    }

    public void deleteConsultation(Long id) {
        consultationRepository.deleteById(id);
    }
}
