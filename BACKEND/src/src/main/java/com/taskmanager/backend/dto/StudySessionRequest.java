package com.taskmanager.backend.dto;

public class StudySessionRequest {

    private String subject;
    private long focusSeconds;
    private long breakSeconds;

    public StudySessionRequest() {}

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public long getFocusSeconds() { return focusSeconds; }
    public void setFocusSeconds(long focusSeconds) { this.focusSeconds = focusSeconds; }

    public long getBreakSeconds() { return breakSeconds; }
    public void setBreakSeconds(long breakSeconds) { this.breakSeconds = breakSeconds; }
}