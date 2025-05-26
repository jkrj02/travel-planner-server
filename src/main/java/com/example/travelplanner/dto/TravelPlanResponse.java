package com.example.travelplanner.dto;

import lombok.Data;
import java.util.List;

@Data
public class TravelPlanResponse {
    private Itinerary itinerary;

    @Data
    public static class Itinerary {
        private List<ItineraryDay> itineraryDays;
    }

    @Data
    public static class ItineraryDay {
        private String date;
        private List<Activity> activities;
    }

    @Data
    public static class Activity {
        private String title;
        private String timeStart;
        private String timeEnd;
        private String location;
        private String description;
    }
} 