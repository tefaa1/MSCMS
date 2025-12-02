package com.example.playerservice.model.embeddable;

import com.example.playerservice.model.enums.Position;
import jakarta.persistence.Embeddable;

@Embeddable
public class PlayerRef {

    private Long id;               // id من UserService
    private String firstName;
    private String lastName;
    private Position preferredPosition;
    private String nationality;
    private Integer kitNumber;

}
