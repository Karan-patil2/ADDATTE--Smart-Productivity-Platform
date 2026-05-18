package com.taskmanager.backend.dto;

public class LeaderboardEntry {

    private String name;
    private long totalFocusSeconds;
    private long sessionCount;
    private int rank;

    public LeaderboardEntry() {}

    public LeaderboardEntry(String name, long totalFocusSeconds, long sessionCount, int rank) {
        this.name = name;
        this.totalFocusSeconds = totalFocusSeconds;
        this.sessionCount = sessionCount;
        this.rank = rank;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public long getTotalFocusSeconds() { return totalFocusSeconds; }
    public void setTotalFocusSeconds(long totalFocusSeconds) { this.totalFocusSeconds = totalFocusSeconds; }

    public long getSessionCount() { return sessionCount; }
    public void setSessionCount(long sessionCount) { this.sessionCount = sessionCount; }

    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }
}