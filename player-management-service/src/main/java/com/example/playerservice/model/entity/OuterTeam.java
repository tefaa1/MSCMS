package com.example.playerservice.model.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "outer_teams")
public class OuterTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String country;

    @OneToMany(mappedBy = "outerTeam", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<OuterPlayer>outerPlayers = new HashSet<>();
}
