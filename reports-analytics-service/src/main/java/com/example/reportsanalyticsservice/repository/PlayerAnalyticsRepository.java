package com.example.reportsanalyticsservice.repository;

import com.example.reportsanalyticsservice.model.entity.PlayerAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerAnalyticsRepository extends JpaRepository<PlayerAnalytics, Long> {
}
