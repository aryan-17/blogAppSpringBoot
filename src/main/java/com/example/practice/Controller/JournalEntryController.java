//package com.example.practice.Controller;
//
//import com.example.practice.entity.JournalEntry;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//@RestController
//@RequestMapping("_journal")
//public class JournalEntryController {
//
//    private final HashMap<Long, JournalEntry> journalEntries = new HashMap<Long, JournalEntry>();
//
//
//    @GetMapping
//    public List<JournalEntry> getAll(){
//        return new ArrayList<>(journalEntries.values());
//    }
//
//    @PostMapping
//    public boolean createEntry(@RequestBody JournalEntry myEntry){
//        journalEntries.put(myEntry.getId(), myEntry);
//        return true;
//    }
//
//    @GetMapping("id/{myId}")
//    public JournalEntry getEntryById(@PathVariable Long myId){
//        return journalEntries.get(myId);
//    }
//
//    @DeleteMapping("id/{myId}")
//    public JournalEntry deleteJournalEntryById(@PathVariable Long myId){
//        return journalEntries.remove(myId);
//    }
//    @PutMapping("id/{myId}")
//    public JournalEntry updateJournalById(@PathVariable Long myId, @RequestBody JournalEntry entry){
//        return journalEntries.put(myId, entry);
//    }
//}