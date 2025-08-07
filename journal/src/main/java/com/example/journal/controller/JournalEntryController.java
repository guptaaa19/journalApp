package com.example.journal.controller;


import com.example.journal.entity.JournalEntry;
import com.example.journal.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll(){
        List<JournalEntry> entries = journalEntryService.getAll();
        return ResponseEntity.ok(entries);
    }

    @PostMapping
    public ResponseEntity<String> createEntry(@RequestBody JournalEntry myEntry) {
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return ResponseEntity.status(201).body("Entry created successfully");
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
        Optional<JournalEntry> result = journalEntryService.findById(myId);
        if(result.isPresent()){
            return ResponseEntity.ok(result.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<String> deleteJournalEntryById(@PathVariable ObjectId myId){
        if(journalEntryService.findById(myId).isPresent()){
            journalEntryService.deleteById(myId);
            return ResponseEntity.ok("Deleted Successfully");
        }else{
            return ResponseEntity.status(404).body("Entry not found");
        }
    }

    @PutMapping("id/{id}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry){
        JournalEntry old = journalEntryService.findById(id).orElse(null);
        if(old != null) {
            return ResponseEntity.notFound().build();
        }
        old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
        old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
        journalEntryService.saveEntry(old);
        return ResponseEntity.ok(old);
    }
}
