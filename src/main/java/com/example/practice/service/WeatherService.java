package com.example.practice.service;

import com.example.practice.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    private static final String apiKey = "";

    private static final String API = "";

    @Autowired
    private RestTemplate restTemplate;
    public WeatherResponse getWeather(String city){
        String finalApi = API.replace("City", city).replace("Key", apiKey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }
}
