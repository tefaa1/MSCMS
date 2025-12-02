package com.example.usermanagementservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "scouts")
public class ScoutProfile extends UserProfile{

    private String region;
    private String organizationName;
}
