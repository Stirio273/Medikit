package com.madaproject.Medikit.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.madaproject.Medikit.dto.PatientDTO;
import com.madaproject.Medikit.models.Patient;
import com.madaproject.Medikit.repository.PatientRepository;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé"));
        return toDTO(patient);
    }

    public PatientDTO createPatient(PatientDTO patientDTO) {
        Patient patient = toEntity(patientDTO);
        patientRepository.save(patient);
        return toDTO(patient);
    }


    public PatientDTO updatePatient(Long id, PatientDTO patientDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé"));

        BeanUtils.copyProperties(patientDTO, patient, "patientId", "createdAt", "updatedAt");
        patientRepository.save(patient);
        return toDTO(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public List<PatientDTO> searchPatients(String keyword) {
        return patientRepository.findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private PatientDTO toDTO(Patient patient) {
        return PatientDTO.builder()
                .patientId(patient.getPatientId())
                .nom(patient.getNom())
                .prenom(patient.getPrenom())
                .naissance(patient.getNaissance())
                .genre(patient.getGenre())
                .phone(patient.getPhone())
                .email(patient.getEmail())
                .adresse(patient.getAdresse())
                .contactUrgence(patient.getContactUrgence())
                .build();
    }

    private Patient toEntity(PatientDTO dto) {
        return Patient.builder()
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .naissance(dto.getNaissance())
                .genre(dto.getGenre())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .adresse(dto.getAdresse())
                .contactUrgence(dto.getContactUrgence())
                .build();
    }
}
