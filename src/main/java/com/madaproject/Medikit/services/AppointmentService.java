package com.madaproject.Medikit.services;
import org.springframework.stereotype.Service;

import com.madaproject.Medikit.models.Appointment;
import com.madaproject.Medikit.repository.AppointmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> getAppointmentsByStatus(String status) {
        return appointmentRepository.findByStatus(status);
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Long id, Appointment updated) {
        return appointmentRepository.findById(id)
                .map(existing -> {
                    existing.setPatient(updated.getPatient());
                    existing.setType(updated.getType());
                    existing.setStatus(updated.getStatus());
                    existing.setPriority(updated.getPriority());
                    existing.setDate(updated.getDate());
                    existing.setTime(updated.getTime());
                    return appointmentRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }
    

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }    
}
