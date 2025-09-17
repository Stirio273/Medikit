package com.madaproject.Medikit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.madaproject.Medikit.models.Appointment;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByStatus(String status);
}