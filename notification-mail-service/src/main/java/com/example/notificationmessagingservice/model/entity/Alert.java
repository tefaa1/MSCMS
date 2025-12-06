package com.example.notificationmessagingservice.model.entity;

import com.example.notificationmessagingservice.model.enums.AlertPriority;
import com.example.notificationmessagingservice.model.enums.AlertType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AlertType alertType;

    @Enumerated(EnumType.STRING)
    private AlertPriority priority;

    private String title;
    private String message;
    private String description;

    private Long targetUserId;  // Who should receive this alert (optional - can be role-based)
    private String targetRole;  // Role that should receive this alert (e.g., "COACH", "DOCTOR")

    private Long relatedEntityId;  // ID of related entity (injury, player, report, etc.)
    private String relatedEntityType;  // Type of related entity

    private LocalDateTime triggeredAt;
    private LocalDateTime acknowledgedAt;
    private Long acknowledgedByUserId;  // Who acknowledged the alert

    private Boolean isAcknowledged;
    private Boolean isResolved;
    private LocalDateTime resolvedAt;

    private String actionRequired;  // What action is needed
    private String metadata;  // Additional alert data (JSON string)
}

