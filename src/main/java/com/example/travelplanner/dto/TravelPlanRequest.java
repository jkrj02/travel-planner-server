package com.example.travelplanner.dto;

import lombok.Data;
import java.util.List;

@Data
public class TravelPlanRequest {
    private String title;
    private List<Long> destinations;
    private String startDate;
    private String endDate;
    private int duration;
    private double totalBudget;
    private Preferences preferences;

    @Data
    public static class Preferences {
        private String pacePreference; // relaxed, moderate, intensive
        private String accommodationType; // budget, mid-range, luxury
        private String transportationType; // public, rental, tour
        private List<String> activityPreferences; // sightseeing, culture, food, shopping, nature, adventure, relaxation
        private String specialRequirements;
    }
} 