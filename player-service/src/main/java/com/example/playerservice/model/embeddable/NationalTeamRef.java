package com.example.playerservice.model.embeddable;

import jakarta.persistence.Embeddable;

@Embeddable
public class NationalTeamRef {
    private Long id;
    private String federationName;
    private String country;
}