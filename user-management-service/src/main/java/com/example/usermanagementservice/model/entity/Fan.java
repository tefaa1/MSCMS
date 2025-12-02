package com.example.usermanagementservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "fans")
public class Fan extends UserProfile{

    private String displayName;
    private Long favoriteTeamId;
}
