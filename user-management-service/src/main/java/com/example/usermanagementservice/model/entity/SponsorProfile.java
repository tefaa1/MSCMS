package com.example.usermanagementservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "sponsors")
public class SponsorProfile extends UserProfile{

    private String companyName;
    private String contactPerson;
    private String email;
    private LocalDate contractStart;
    private LocalDate contractEnd;
}
