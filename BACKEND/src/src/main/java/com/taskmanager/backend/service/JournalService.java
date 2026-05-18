package com.taskmanager.backend.service;

import com.taskmanager.backend.dto.JournalRequest;
import com.taskmanager.backend.model.Journal;
import com.taskmanager.backend.model.User;
import com.taskmanager.backend.repository.JournalRepository;
import com.taskmanager.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private UserRepository userRepository;

    public Journal createEntry(JournalRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Journal journal = new Journal();
        journal.setContent(request.getContent());
        journal.setMood(request.getMood());
        journal.setUser(user);

        return journalRepository.save(journal);
    }

    public List<Journal> getEntries(String email) {
        return journalRepository.findByUserEmailOrderByCreatedAtDesc(email);
    }

    public void deleteEntry(Long id, String email) {
        Journal journal = journalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        if (!journal.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }

        journalRepository.deleteById(id);
    }
}