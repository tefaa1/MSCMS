package com.example.playerservice.model.entity;

import com.example.playerservice.model.embeddable.PlayerRef;
import jakarta.persistence.*;

@Entity
@Table(name = "rosters")
public class Roster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PlayerRef player;

    private String season;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
