package com.example.playerservice.model.entity;

import com.example.playerservice.model.embeddable.PlayerRef;
import com.example.playerservice.model.enums.RequestStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "players_transfers_outgoing")
public class PlayerTransferOutgoing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PlayerRef player;

    @ManyToOne
    @JoinColumn(name = "from_team_id")
    private Team fromTeam;

    @ManyToOne
    @JoinColumn(name = "to_team_id")
    private OuterTeam toTeam;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private LocalDate requestDate;
}
