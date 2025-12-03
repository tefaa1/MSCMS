package com.example.usermanagementservice.model.entity;

import com.example.usermanagementservice.model.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "scouts")
public class Scout extends User {

    private String region;
    private String organizationName;

    @Enumerated(EnumType.STRING)
    private Role role = Role.SCOUT;

}
