package com.madaproject.Medikit.repository;

import com.madaproject.Medikit.models.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findByPatientPatientIdOrderByCreatedAtDesc(Long patientId);
    List<Attachment> findByPatientPatientIdAndFileType(Long patientId, String fileType);
}
