package com.taskmanager.backend.controller;

import com.taskmanager.backend.dto.LeaderboardEntry;
import com.taskmanager.backend.dto.StudySessionRequest;
import com.taskmanager.backend.model.StudySession;
import com.taskmanager.backend.service.StudySessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/study")
public class StudySessionController {

    @Autowired
    private StudySessionService studySessionService;

    @PostMapping("/session")
    public ResponseEntity<StudySession> saveSession(
            @RequestBody StudySessionRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        StudySession saved = studySessionService.saveSession(request, userDetails.getUsername());
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<StudySession>> getMySessions(
            @AuthenticationPrincipal UserDetails userDetails) {

        List<StudySession> sessions = studySessionService.getUserSessions(userDetails.getUsername());
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<LeaderboardEntry>> getLeaderboard() {
        return ResponseEntity.ok(studySessionService.getLeaderboard());
    }
}