package com.taskmanager.backend.controller;

import com.taskmanager.backend.dto.JournalRequest;
import com.taskmanager.backend.model.Journal;
import com.taskmanager.backend.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/journal")
public class JournalController {

    @Autowired
    private JournalService journalService;

    @PostMapping
    public ResponseEntity<Journal> create(
            @RequestBody JournalRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                journalService.createEntry(request, userDetails.getUsername())
        );
    }

    @GetMapping
    public ResponseEntity<List<Journal>> getAll(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                journalService.getEntries(userDetails.getUsername())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        journalService.deleteEntry(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}