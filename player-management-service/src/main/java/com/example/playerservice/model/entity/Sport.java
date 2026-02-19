package com.example.playerservice.model.entity;

import com.example.playerservice.model.enums.SportType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sports")
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long sportManagerId;

    @Enumerated(EnumType.STRING)
    private SportType sportType;

    @OneToMany(mappedBy = "sport", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Team> teams = new HashSet<>();
}
