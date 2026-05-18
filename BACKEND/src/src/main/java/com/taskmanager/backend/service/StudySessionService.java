package com.taskmanager.backend.service;

import com.taskmanager.backend.dto.LeaderboardEntry;
import com.taskmanager.backend.dto.StudySessionRequest;
import com.taskmanager.backend.model.StudySession;
import com.taskmanager.backend.model.User;
import com.taskmanager.backend.repository.StudySessionRepository;
import com.taskmanager.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudySessionService {

    @Autowired
    private StudySessionRepository studySessionRepository;

    @Autowired
    private UserRepository userRepository;

    public StudySession saveSession(StudySessionRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        StudySession session = new StudySession();
        session.setSubject(request.getSubject());
        session.setFocusSeconds(request.getFocusSeconds());
        session.setBreakSeconds(request.getBreakSeconds());
        session.setDate(LocalDate.now());
        session.setUser(user);

        return studySessionRepository.save(session);
    }

    public List<StudySession> getUserSessions(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return studySessionRepository.findByUserOrderByDateDesc(user);
    }

    public List<LeaderboardEntry> getLeaderboard() {
        List<Object[]> raw = studySessionRepository.getLeaderboardRaw();
        List<LeaderboardEntry> leaderboard = new ArrayList<>();

        for (int i = 0; i < raw.size(); i++) {
            Object[] row = raw.get(i);

            String email = (String) row[0];
            long totalFocusSeconds = ((Number) row[1]).longValue();
            long sessionCount = ((Number) row[2]).longValue();

            String name = email;
            java.util.Optional<User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent()) {
                User u = userOpt.get();
                name = u.getUsername();
            }

            leaderboard.add(new LeaderboardEntry(name, totalFocusSeconds, sessionCount, i + 1));
        }

        return leaderboard;
    }
}