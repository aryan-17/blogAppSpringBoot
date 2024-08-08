package com.example.practice.service;

import com.example.practice.api.response.WeatherResponse;
import com.example.practice.cache.AppCache;
import com.example.practice.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private static final String API = "";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;
    public WeatherResponse getWeather(String city){
//        Similarly we can use apiUrl from application.yml.
        String finalApi = appCache.APP_CACHE.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.CITY, city).replace(Placeholders.API_KEY, apiKey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }
}
