package com.madaproject.Medikit.controllers;

import org.springframework.web.bind.annotation.*;

import com.madaproject.Medikit.models.Appointment;
import com.madaproject.Medikit.services.AppointmentService;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*") 
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/status/{status}")
    public List<Appointment> getByStatus(@PathVariable String status) {
        return appointmentService.getAppointmentsByStatus(status);
    }

    @GetMapping("/{id}")
    public Appointment getAppointment(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id).orElse(null);
    }

    @PostMapping
    public Appointment create(@RequestBody Appointment appointment) {
        if (appointment.getPatient() == null || appointment.getPatient().getPatientId() == null) {
            throw new RuntimeException("Patient non spécifié");
        }
        return appointmentService.createAppointment(appointment);
    }


    @PutMapping("/{id}")
    public Appointment update(@PathVariable Long id, @RequestBody Appointment appointment) {
        return appointmentService.updateAppointment(id, appointment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
    }
}