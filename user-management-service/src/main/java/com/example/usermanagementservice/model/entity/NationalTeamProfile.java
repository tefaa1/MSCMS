package com.example.usermanagementservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "national_team_profile")
public class NationalTeamProfile extends UserProfile{

    private String federationName;  // اسم الاتحاد الوطني لكرة القدم
    private String contactPerson;   // الشخص المسؤول للتواصل
    private String email;           // الإيميل الرسمي للتواصل
    private String country;
}
