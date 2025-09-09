package com.madaproject.Medikit.repository;
import com.madaproject.Medikit.models.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long>{
    List<MedicalHistory> findByPatientPatientIdOrderByCreatedAtDesc(Long patientId);
    List<MedicalHistory> findByPatientPatientIdAndTypeOrderByCreatedAtDesc(Long patientId, String type);
}
