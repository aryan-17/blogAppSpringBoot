package com.example.practice.scheduler;

import com.example.practice.entity.JournalEntry;
import com.example.practice.entity.User;
import com.example.practice.enums.Sentiment;
import com.example.practice.repository.UserRepositoryImpl;
import com.example.practice.service.EmailService;
import com.example.practice.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUserAndSendEmail(){
        List<User> users = userRepository.getUserForSA();

        for(User user: users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer>  sentimentCounts = new HashMap<>();
            for(Sentiment sentiment:sentiments){
                if(sentiment!=null){
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment,0)+1);
                }
                Sentiment mostFrequentSentiments = null;
                int max = 0;
                for(Map.Entry<Sentiment, Integer> entry:sentimentCounts.entrySet()){
                    if(entry.getValue()>max){
                        max = entry.getValue();
                        mostFrequentSentiments = entry.getKey();
                    }
                }
                if(mostFrequentSentiments != null){
                    emailService.sendEmail(user.getEmail(), "Sentiment for past 7 days", mostFrequentSentiments.toString());
                }
            }
        }

    }
}
