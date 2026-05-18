package com.taskmanager.backend.service;

import com.taskmanager.backend.model.Task;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class PriorityEngine {

    public void calculate(Task task) {
        int score = 0;

        // Rule 1 — deadline proximity
        if (task.getDeadline() != null) {
            long daysLeft = ChronoUnit.DAYS.between(
                    LocalDate.now(), task.getDeadline()
            );
            if (daysLeft <= 1)       score += 40;
            else if (daysLeft <= 3)  score += 30;
            else if (daysLeft <= 7)  score += 20;
            else if (daysLeft <= 14) score += 10;
        }

        // Rule 2 — skip/delay pattern
        if (task.getSkipCount() >= 3)      score += 30;
        else if (task.getSkipCount() >= 1) score += 15;

        // Rule 3 — category weight
        if (task.getCategory() != null) {
            switch (task.getCategory().toLowerCase()) {
                case "study":
                case "work":  score += 20; break;
                case "health": score += 10; break;
                default: score += 5;
            }
        }

        // Set score and label
        task.setPriorityScore(score);

        if (score >= 60)       task.setPriorityLabel("HIGH");
        else if (score >= 30)  task.setPriorityLabel("MEDIUM");
        else                   task.setPriorityLabel("LOW");
    }
}