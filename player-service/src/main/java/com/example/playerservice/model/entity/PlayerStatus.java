package com.example.playerservice.model.entity;

import com.example.playerservice.model.embeddable.PlayerRef;
import com.example.playerservice.model.enums.StatusOfPlayer;
import jakarta.persistence.*;

@Entity
@Table(name = "players_status")
public class PlayerStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PlayerRef player;

    @Enumerated(EnumType.STRING)
    private StatusOfPlayer statusOfPlayer;

}
