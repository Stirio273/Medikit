package com.madaproject.Medikit.controllers;

import com.madaproject.Medikit.dto.ConsultationResponseDTO;
import com.madaproject.Medikit.services.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/consultations")
public class GlobalConsultationController {
    
    @Autowired
    private ConsultationService consultationService;

    @GetMapping
    public List<ConsultationResponseDTO> getAllConsultationsOrderByDateDesc() {
        return consultationService.getAllConsultationsOrderByDateDesc();
    }
}