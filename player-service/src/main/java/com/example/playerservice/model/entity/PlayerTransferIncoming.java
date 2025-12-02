package com.example.playerservice.model.entity;

import com.example.playerservice.model.embeddable.PlayerRef;
import com.example.playerservice.model.enums.RequestStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "player_transfer_incoming")
public class PlayerTransferIncoming {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PlayerRef player;

    @ManyToOne
    @JoinColumn(name = "from_team_id")
    private OuterTeam fromTeam;

    @ManyToOne
    @JoinColumn(name = "to_team_id")
    private Team toTeam;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private LocalDate requestDate;
}
