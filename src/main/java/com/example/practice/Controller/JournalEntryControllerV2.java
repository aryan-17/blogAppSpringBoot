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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public List<JournalEntry> getAllJournalEntriesOfUser(@PathVariable String username){
        User user = userService.findByUsername(username);
        return user.getJournalEntries();
    }

    @PostMapping("{username}")
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry, @PathVariable String username){
        journalEntryService.saveEntry(myEntry, username);
        return myEntry;
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId myId){
//        return journalEntryService.findById(myId).orElse(null);

        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("id/{username}/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String username){
        journalEntryService.deleteById(myId, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("id/{myId}")
    public JournalEntry updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry entry){
        JournalEntry existingEntry = journalEntryService.findById(myId).orElse(null);
        if(existingEntry != null){
            existingEntry.setTitle(!entry.getTitle().isEmpty() ? entry.getTitle() : existingEntry.getTitle());
            existingEntry.setContent(entry.getContent() != null && !entry.getContent().isEmpty() ? entry.getContent() : existingEntry.getContent());
        }

//        journalEntryService.saveEntry(existingEntry,);
        return entry;
    }
}