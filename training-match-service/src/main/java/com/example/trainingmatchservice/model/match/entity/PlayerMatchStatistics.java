package com.example.trainingmatchservice.model.match.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "players_matches_statistics")
public class PlayerMatchStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    private Long playerId;

    private Integer minutesPlayed;

    // Offensive stats
    private Integer goals;
    private Integer assists;
    private Integer shots;
    private Integer shotsOnTarget;
    private Integer dribbles;
    private Integer dribblesSuccessful;

    // Passing stats
    private Integer passes;
    private Integer passesCompleted;
    private Integer keyPasses;

    // Defensive stats
    private Integer tackles;
    private Integer tacklesWon;
    private Integer interceptions;
    private Integer clearances;

    // Discipline
    private Integer foulsCommitted;
    private Integer foulsWon;
    private Integer yellowCards;
    private Integer redCards;

    // Overall rating
    private Double performanceRating;  // 0-10
}
