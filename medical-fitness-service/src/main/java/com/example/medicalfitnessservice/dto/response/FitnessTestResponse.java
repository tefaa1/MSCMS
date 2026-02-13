package com.example.medicalfitnessservice.dto.response;

import com.example.medicalfitnessservice.model.enums.FitnessTestType;

import java.time.LocalDateTime;

public record FitnessTestResponse(
        Long id,
        String playerKeycloakId,
        Long teamId,
        FitnessTestType testType,
        LocalDateTime testDate,
        String conductedByDoctorKeycloakId,
        String testName,
        Double result,
        String unit,
        String resultCategory,
        String notes,
        String recommendations,
        String attachments
) {}

