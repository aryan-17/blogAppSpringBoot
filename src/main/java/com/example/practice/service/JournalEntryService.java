package com.example.practice.service;

import com.example.practice.entity.JournalEntry;
import com.example.practice.entity.User;
import com.example.practice.repository.JournalEntryRepository;
import jakarta.transaction.Transactional;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username){
        User user = userService.findByUsername(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveUser(user);
    }
    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }
    @Transactional
    public Boolean deleteById(ObjectId id, String username){
        try {
            User user = userService.findByUsername(username);
            Boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
            return removed;
        }
        catch (Exception e){
            logger.info("Error in deleting for {}: ", username, e);
            return false;
        }
    }
//    public List<JournalEntry> findByUsername(String username){
//
//    }
}
