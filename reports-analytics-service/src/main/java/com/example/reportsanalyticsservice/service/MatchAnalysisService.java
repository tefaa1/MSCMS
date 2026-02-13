package com.example.reportsanalyticsservice.service;

import com.example.reportsanalyticsservice.dto.request.MatchAnalysisRequest;
import com.example.reportsanalyticsservice.dto.response.MatchAnalysisResponse;
import java.util.List;

public interface MatchAnalysisService {
    MatchAnalysisResponse create(MatchAnalysisRequest request);
    MatchAnalysisResponse update(Long id, MatchAnalysisRequest request);
    void delete(Long id);
    MatchAnalysisResponse getById(Long id);
    List<MatchAnalysisResponse> getAll();
}
