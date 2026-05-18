package com.taskmanager.backend.service;

import com.taskmanager.backend.dto.CompletionDataPoint;
import com.taskmanager.backend.dto.ProductivityResponse;
import com.taskmanager.backend.model.Task;
import com.taskmanager.backend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    @Autowired
    private TaskRepository taskRepository;

    public List<CompletionDataPoint> getCompletionData(String email, int days) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);

        List<Task> allTasks = taskRepository.findByUserEmailAndCreatedAtAfter(email, since);
        List<Task> completedTasks = taskRepository.findByUserEmailAndStatusAndCompletedAtAfter(
                email, "COMPLETED", since);

        // Group by date
        Map<LocalDate, Long> totalByDate = allTasks.stream()
                .collect(Collectors.groupingBy(t -> t.getCreatedAt().toLocalDate(), Collectors.counting()));

        Map<LocalDate, Long> completedByDate = completedTasks.stream()
                .collect(Collectors.groupingBy(t -> t.getCompletedAt().toLocalDate(), Collectors.counting()));

        // Build last N days
        List<CompletionDataPoint> result = new ArrayList<>();
        for (int i = days - 1; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            long total = totalByDate.getOrDefault(date, 0L);
            long completed = completedByDate.getOrDefault(date, 0L);
            result.add(new CompletionDataPoint(date, completed, total));
        }
        return result;
    }

    public ProductivityResponse getProductivity(String email) {
        LocalDateTime since = LocalDateTime.now().minusDays(30);

        List<Task> completedTasks = taskRepository.findByUserEmailAndStatusAndCompletedAtAfter(
                email, "COMPLETED", since);

        // Group completed tasks by date
        Set<LocalDate> completedDates = completedTasks.stream()
                .map(t -> t.getCompletedAt().toLocalDate())
                .collect(Collectors.toSet());

        // Calculate streaks
        int currentStreak = 0;
        int bestStreak = 0;
        int tempStreak = 0;

        for (int i = 0; i < 30; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            if (completedDates.contains(date)) {
                tempStreak++;
                if (i == 0 || currentStreak == i) currentStreak = tempStreak;
            } else {
                bestStreak = Math.max(bestStreak, tempStreak);
                tempStreak = 0;
            }
        }
        bestStreak = Math.max(bestStreak, tempStreak);

        // Productivity score = (completed / total tasks in last 30 days) * 100
        List<Task> allTasks = taskRepository.findByUserEmailAndCreatedAtAfter(email, since);
        double score = allTasks.isEmpty() ? 0.0
                : Math.min(100.0, (completedTasks.size() * 1.0 / allTasks.size()) * 100.0);

        return new ProductivityResponse(currentStreak, bestStreak, Math.round(score * 10.0) / 10.0);
    }
}