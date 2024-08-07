package com.example.practice.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse {
    private Cord cord;
    private ArrayList<Weather> weather;
    private String base;
    private Main main;
    private int visibility;
    private Wind wind;
    private Rain rain;
    private Clouds clouds;
    private long dt;
    private Sys sys;
    private int timezone;
    private int id;
    private String name;
    private int cod;

    // Getters and Setters

    @Data
    public static class Cord {
        private double lon;
        private double lat;

        // Getters and Setters
    }

    @Data
    public static class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;

        // Getters and Setters
    }

    @Data
    public static class Main {
        private double temp;
        private double feels_like;
        private double temp_min;
        private double temp_max;
        private int pressure;
        private int humidity;
        @JsonProperty("sea_level")
        private int seaLevel;
        @JsonProperty("grnd_level")
        private int groundLevel;

        // Getters and Setters
    }
    @Data
    public static class Wind {
        private double speed;
        private int deg;
        private double gust;

        // Getters and Setters
    }
    @Data
    public static class Rain {
        private double _1h;

        // Getters and Setters
    }
    @Data
    public static class Clouds {
        private int all;

        // Getters and Setters
    }
    @Data
    public static class Sys {
        private int type;
        private int id;
        private String country;
        private long sunrise;
        private long sunset;

        // Getters and Setters
    }
}

