package com.madaproject.Medikit.repository;
import com.madaproject.Medikit.models.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long>{
    // Trouver toutes les prescriptions d'une consultation
    List<Prescription> findByConsultationConsultationIdOrderByCreatedAtDesc(Long consultationId);
    
    // Trouver les prescriptions actives d'un patient
    @Query("SELECT p FROM Prescription p WHERE p.consultation.patient.patientId = :patientId AND p.isActive = true")
    List<Prescription> findActivePrescriptionsByPatientId(@Param("patientId") Long patientId);
    
    // Trouver les prescriptions par médicament
    List<Prescription> findByMedicationContainingIgnoreCase(String medication);
    
    // Trouver les prescriptions qui expirent bientôt
    @Query("SELECT p FROM Prescription p WHERE p.endDate BETWEEN :startDate AND :endDate")
    List<Prescription> findPrescriptionsExpiringBetween(@Param("startDate") LocalDate startDate, 
                                                       @Param("endDate") LocalDate endDate);
    
    // Trouver les prescriptions d'un patient spécifique
    @Query("SELECT p FROM Prescription p WHERE p.consultation.patient.patientId = :patientId")
    List<Prescription> findByPatientId(@Param("patientId") Long patientId);
    
    // Compter le nombre de prescriptions actives par patient
    @Query("SELECT COUNT(p) FROM Prescription p WHERE p.consultation.patient.patientId = :patientId AND p.isActive = true")
    Long countActivePrescriptionsByPatientId(@Param("patientId") Long patientId);
}
