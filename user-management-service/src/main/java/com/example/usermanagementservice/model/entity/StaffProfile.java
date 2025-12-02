package com.example.usermanagementservice.model.entity;

import com.example.usermanagementservice.model.enums.StaffRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "staff")
public class StaffProfile extends UserProfile{

    @Enumerated(EnumType.STRING)
    private StaffRole staffRole;

    private String jobTitle;

    private Long sportId;
    private Long teamId;
}
