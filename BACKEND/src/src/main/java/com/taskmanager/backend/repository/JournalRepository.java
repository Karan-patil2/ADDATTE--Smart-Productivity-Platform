package com.taskmanager.backend.repository;

import com.taskmanager.backend.model.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {
    List<Journal> findByUserEmailOrderByCreatedAtDesc(String email);
}