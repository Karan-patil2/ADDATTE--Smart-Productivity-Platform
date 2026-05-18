package com.taskmanager.backend.dto;

import java.time.LocalDate;

public class CompletionDataPoint {
    private LocalDate date;
    private long completed;
    private long total;

    public CompletionDataPoint(LocalDate date, long completed, long total) {
        this.date = date;
        this.completed = completed;
        this.total = total;
    }

    // Getters
    public LocalDate getDate() { return date; }
    public long getCompleted() { return completed; }
    public long getTotal() { return total; }
}