package com.example.usermanagementservice.model.entity;

import com.example.usermanagementservice.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sponsors")
public class Sponsor extends User {

    private String companyName;

    @Enumerated(EnumType.STRING)
    private Role role = Role.SPONSOR;
}
