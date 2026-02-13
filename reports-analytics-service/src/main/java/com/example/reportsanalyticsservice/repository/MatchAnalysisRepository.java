package com.example.reportsanalyticsservice.repository;

import com.example.reportsanalyticsservice.model.entity.MatchAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchAnalysisRepository extends JpaRepository<MatchAnalysis, Long> {
}
