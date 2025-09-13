package com.madaproject.Medikit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.madaproject.Medikit.models.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String nom, String prenom);

    // Recherche par nom (insensible à la casse)
    List<Patient> findByNomContainingIgnoreCase(String nom);
    
    // Recherche par prénom (insensible à la casse)
    List<Patient> findByPrenomContainingIgnoreCase(String prenom);
    
    // Recherche par email (insensible à la casse)
    List<Patient> findByEmailContainingIgnoreCase(String email);
    
    // Recherche combinée par nom, prénom ou email (insensible à la casse)
    List<Patient> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String nom, String prenom, String email);
    
    // Recherche avancée avec requête personnalisée
    @Query("SELECT p FROM Patient p WHERE " +
           "LOWER(p.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.prenom) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "CAST(p.patientId AS string) LIKE CONCAT('%', :keyword, '%')")
    List<Patient> advancedSearch(@Param("keyword") String keyword);
}
