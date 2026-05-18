package com.taskmanager.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "study_sessions")
public class StudySession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    private long focusSeconds;

    private long breakSeconds;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public StudySession() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public long getFocusSeconds() { return focusSeconds; }
    public void setFocusSeconds(long focusSeconds) { this.focusSeconds = focusSeconds; }

    public long getBreakSeconds() { return breakSeconds; }
    public void setBreakSeconds(long breakSeconds) { this.breakSeconds = breakSeconds; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}