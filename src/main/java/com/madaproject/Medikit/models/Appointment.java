package com.madaproject.Medikit.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // ex: "Consultation générale"
    private String status; // confirmé, en attente, terminé, annulé
    private String priority; // normal, urgent
    private String date; // "Aujourd'hui", "Demain", "12/06/2023"
    private String time; // "10:00 - 10:30"
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient; 

    // Constructeurs
    public Appointment() {}

    public Appointment(String type, String status, String priority, String date, String time, Patient patient) {
        this.type = type;
        this.status = status;
        this.priority = priority;
        this.date = date;
        this.time = time;
        this.patient = patient;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
}
