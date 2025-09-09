package com.madaproject.Medikit.repository;
import com.madaproject.Medikit.models.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long>{
    List<Consultation> findByPatientPatientIdOrderByConsultationDateDesc(Long patientId);
    List<Consultation> findByConsultationDateBetween(LocalDateTime start, LocalDateTime end);
}
