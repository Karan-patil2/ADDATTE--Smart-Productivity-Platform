package com.taskmanager.backend.repository;

import com.taskmanager.backend.model.StudySession;
import com.taskmanager.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudySessionRepository extends JpaRepository<StudySession, Long> {

    List<StudySession> findByUserOrderByDateDesc(User user);

    @Query("SELECT s.user.email, SUM(s.focusSeconds), COUNT(s) " +
            "FROM StudySession s " +
            "GROUP BY s.user.email " +
            "ORDER BY SUM(s.focusSeconds) DESC")
    List<Object[]> getLeaderboardRaw();
}