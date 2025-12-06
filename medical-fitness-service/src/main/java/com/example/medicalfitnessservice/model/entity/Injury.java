package com.example.medicalfitnessservice.model.entity;

import com.example.medicalfitnessservice.model.enums.InjurySeverity;
import com.example.medicalfitnessservice.model.enums.InjuryStatus;
import com.example.medicalfitnessservice.model.enums.InjuryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "injuries")
public class Injury {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long playerId;
    private Long teamId;

    @Enumerated(EnumType.STRING)
    private InjuryType injuryType;

    @Enumerated(EnumType.STRING)
    private InjurySeverity severity;

    @Enumerated(EnumType.STRING)
    private InjuryStatus status;

    private String bodyPart;  // e.g., "Left Knee", "Right Ankle"
    private String description;
    private LocalDate injuryDate;
    private LocalDateTime reportedAt;

    private Long reportedByDoctorId;  // Reference to Doctor

    @OneToMany(mappedBy = "injury", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Diagnosis> diagnoses = new HashSet<>();

    @OneToMany(mappedBy = "injury", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Treatment> treatments = new HashSet<>();

    @OneToMany(mappedBy = "injury", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rehabilitation> rehabilitations = new HashSet<>();
}

