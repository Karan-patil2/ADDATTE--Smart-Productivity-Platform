package com.taskmanager.backend.controller;

import com.taskmanager.backend.dto.CompletionDataPoint;
import com.taskmanager.backend.dto.ProductivityResponse;
import com.taskmanager.backend.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/completion")
    public ResponseEntity<List<CompletionDataPoint>> getCompletion(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "7") int days) {
        return ResponseEntity.ok(
                analyticsService.getCompletionData(userDetails.getUsername(), days)
        );
    }

    @GetMapping("/productivity")
    public ResponseEntity<ProductivityResponse> getProductivity(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                analyticsService.getProductivity(userDetails.getUsername())
        );
    }
}