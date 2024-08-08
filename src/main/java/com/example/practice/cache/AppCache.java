package com.example.practice.cache;

import com.example.practice.entity.ConfigJournalAppEntity;
import com.example.practice.repository.ConfigJournalRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    @Autowired
    private ConfigJournalRepository configJournalRepository;
    public Map<String, String> APP_CACHE;

    public enum keys{
        WEATHER_API
    }

    @PostConstruct
    public void init(){
        APP_CACHE = new HashMap<>();
        List<ConfigJournalAppEntity> all = configJournalRepository.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity:all){
            APP_CACHE.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }
}
