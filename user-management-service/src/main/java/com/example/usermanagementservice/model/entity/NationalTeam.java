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
@Table(name = "national_team_profile")
public class NationalTeam extends User {

    private String federationName;  // اسم الاتحاد الوطني لكرة القدم
    private String contactPerson;   // الشخص المسؤول للتواصل
    private String country;

    @Enumerated(EnumType.STRING)
    private Role role = Role.NATIONAL_TEAM;

}
