package com.example.playerservice.model.entity;

import com.example.playerservice.model.embeddable.PlayerRef;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "players_contracts")
public class PlayerContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PlayerRef player;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private LocalDate startDate;
    private LocalDate endDate;
    private Long salary;
    private Long releaseClause; // if a team wants to buy this player before the contract ends should pay this
}
