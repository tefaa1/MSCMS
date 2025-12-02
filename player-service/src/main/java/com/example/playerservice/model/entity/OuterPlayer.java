package com.example.playerservice.model.entity;

import com.example.playerservice.model.embeddable.PlayerRef;
import jakarta.persistence.*;

@Entity
@Table(name = "outer_players")
public class OuterPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PlayerRef playerRef;

    @ManyToOne
    @JoinColumn(name = "outer_team_id")
    private OuterTeam outerTeam;
}
