package com.taskmanager.backend.service;

import com.taskmanager.backend.model.Task;
import com.taskmanager.backend.model.User;
import com.taskmanager.backend.repository.TaskRepository;
import com.taskmanager.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PriorityEngine priorityEngine;

    public Task createTask(Task task, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        task.setUser(user);
        priorityEngine.calculate(task);
        return taskRepository.save(task);
    }

    public List<Task> getUserTasks(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return taskRepository.findByUserId(user.getId());
    }

    public Task updateTask(Long taskId, Task updated, String email) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setTitle(updated.getTitle());
        task.setDescription(updated.getDescription());
        task.setCategory(updated.getCategory());
        task.setDeadline(updated.getDeadline());
        task.setStatus(updated.getStatus());
        if (updated.getStatus().equals("COMPLETED")) {
            task.setCompletedAt(LocalDateTime.now());
        }
        priorityEngine.calculate(task);
        return taskRepository.save(task);
    }

    public Task skipTask(Long taskId, String email) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setSkipCount(task.getSkipCount() + 1);
        priorityEngine.calculate(task);
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId, String email) {
        taskRepository.deleteById(taskId);
    }

    public Task completeTask(Long taskId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        task.setStatus("COMPLETED");
        task.setCompletedAt(LocalDateTime.now());
        task.setPriorityScore(0);
        task.setPriorityLabel("LOW");
        return taskRepository.save(task);
    }
}