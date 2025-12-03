package com.example.playerservice.model.entity;

import com.example.playerservice.model.enums.Position;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "outer_players")
public class OuterPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateOfBirth;
    private String nationality;
    private Position preferredPosition;
    private Long marketValue;
    private Integer kitNumber;

    @ManyToOne
    @JoinColumn(name = "outer_team_id")
    private OuterTeam outerTeam;

    @OneToMany(mappedBy = "outerPlayer", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<PlayerTransferIncoming>playerTransferIncomingSet = new HashSet<>();
}
