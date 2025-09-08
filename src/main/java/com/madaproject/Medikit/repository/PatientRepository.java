package com.madaproject.Medikit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madaproject.Medikit.models.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String nom, String prenom);
}
