package com.taskmanager.backend.repository;

import com.taskmanager.backend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // --- Existing ---
    List<Task> findByUserId(Long userId);
    List<Task> findByUserIdAndStatus(Long userId, String status);
    List<Task> findByProjectId(Long projectId);
    long countByUserIdAndStatus(Long userId, String status);
    long countByUserIdAndPriorityLabel(Long userId, String priorityLabel);

    // --- Added for Analytics ---
    List<Task> findByUserEmailAndCreatedAtAfter(String email, LocalDateTime after);
    List<Task> findByUserEmailAndStatusAndCompletedAtAfter(String email, String status, LocalDateTime after);
}