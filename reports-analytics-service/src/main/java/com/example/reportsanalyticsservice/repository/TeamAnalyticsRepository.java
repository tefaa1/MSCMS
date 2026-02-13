package com.example.reportsanalyticsservice.repository;

import com.example.reportsanalyticsservice.model.entity.TeamAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamAnalyticsRepository extends JpaRepository<TeamAnalytics, Long> {
}
