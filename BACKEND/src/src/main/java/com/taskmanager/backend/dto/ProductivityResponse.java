package com.taskmanager.backend.dto;

public class ProductivityResponse {
    private int currentStreak;
    private int bestStreak;
    private double productivityScore; // 0.0 - 100.0

    public ProductivityResponse(int currentStreak, int bestStreak, double productivityScore) {
        this.currentStreak = currentStreak;
        this.bestStreak = bestStreak;
        this.productivityScore = productivityScore;
    }

    // Getters
    public int getCurrentStreak() { return currentStreak; }
    public int getBestStreak() { return bestStreak; }
    public double getProductivityScore() { return productivityScore; }
}