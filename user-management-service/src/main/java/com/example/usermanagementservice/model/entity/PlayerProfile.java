package com.example.usermanagementservice.model.entity;

import com.example.usermanagementservice.model.enums.Position;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "players")
public class PlayerProfile extends UserProfile{


    private LocalDate dateOfBirth;
    private String nationality;
    private Position preferredPosition;
    private Long marketValue;
    private Integer kitNumber; // from 1 to 99 handle in request dto
}
