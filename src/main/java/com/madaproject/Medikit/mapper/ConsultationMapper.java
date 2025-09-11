package com.madaproject.Medikit.mapper;

import com.madaproject.Medikit.dto.ConsultationResponseDTO;
import com.madaproject.Medikit.dto.DoctorDTO;
import com.madaproject.Medikit.models.Consultation;
import com.madaproject.Medikit.models.User;

import org.springframework.stereotype.Component;

@Component
public class ConsultationMapper {

    public ConsultationResponseDTO toResponseDTO(Consultation consultation) {
        ConsultationResponseDTO dto = new ConsultationResponseDTO();
        dto.setConsultationId(consultation.getConsultationId());
        
        // Map doctor information
        if (consultation.getDoctor() != null) {
            dto.setDoctorId(consultation.getDoctor().getUser_id());
            dto.setDoctorName(consultation.getDoctor().getUsername());
        }
        
        dto.setConsultationDate(consultation.getConsultationDate());
        dto.setReason(consultation.getReason());
        dto.setObservations(consultation.getObservations());
        dto.setDiagnosis(consultation.getDiagnosis());
        dto.setTreatmentPlan(consultation.getTreatmentPlan());
        
        // Map patient information
        if (consultation.getPatient() != null) {
            dto.setPatientId(consultation.getPatient().getPatientId());
            dto.setPatientName(consultation.getPatient().getPrenom() + " " + consultation.getPatient().getNom());
        }
        
        dto.setCreatedAt(consultation.getCreatedAt());
        dto.setUpdatedAt(consultation.getUpdatedAt());
        
        return dto;
    }
    
    public DoctorDTO toDoctorDTO(User doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setUserId(doctor.getUser_id());
        dto.setUserName(doctor.getUsername());
        return dto;
    }
}