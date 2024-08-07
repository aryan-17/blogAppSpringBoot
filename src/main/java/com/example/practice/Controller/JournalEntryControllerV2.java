package com.example.practice.Controller;

import com.example.practice.entity.JournalEntry;
import com.example.practice.entity.User;
import com.example.practice.service.JournalEntryService;
import com.example.practice.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<JournalEntry> getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        return user.getJournalEntries();
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        journalEntryService.saveEntry(myEntry, username);
        return myEntry;
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x-> x.getId().equals(myId)).toList();
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean removed = journalEntryService.deleteById(myId, username);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @PutMapping("id/{myId}")
    public JournalEntry updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry entry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x-> x.getId().equals(myId)).toList();
        if(!collect.isEmpty()) {
            JournalEntry existingEntry = journalEntryService.findById(myId).orElse(null);
            if (existingEntry != null) {
                existingEntry.setTitle(!entry.getTitle().isEmpty() ? entry.getTitle() : existingEntry.getTitle());
                existingEntry.setContent(entry.getContent() != null && !entry.getContent().isEmpty() ? entry.getContent() : existingEntry.getContent());
            }
        }
        return entry;
    }
}