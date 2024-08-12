package com.example.practice.service;

import com.example.practice.entity.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class SentimentConsumerService {

    @Autowired
    EmailService emailService;

    @KafkaListener(topics = "weekly-sentiments", groupId = "weekly-sentiment-group")
    public void consume(SentimentData sentimentData){
        emailService.sendEmail(sentimentData.getEmail(), "Sentiment for Previous Week ", sentimentData.getSentiment());
    }

}
