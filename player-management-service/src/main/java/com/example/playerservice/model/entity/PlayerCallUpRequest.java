package com.example.playerservice.model.entity;

import com.example.playerservice.model.embeddable.NationalTeamRef;
import com.example.playerservice.model.embeddable.PlayerRef;
import com.example.playerservice.model.enums.RequestStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "players-call-up-requests")
public class PlayerCallUpRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PlayerRef player;

    @Embedded
    private NationalTeamRef nationalTeam;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private LocalDate requestDate;
}
