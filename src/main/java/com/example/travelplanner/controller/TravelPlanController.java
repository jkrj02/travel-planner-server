package com.example.travelplanner.controller;

import com.example.travelplanner.dto.TravelPlanRequest;
import com.example.travelplanner.dto.TravelPlanResponse;
import com.example.travelplanner.service.AITravelPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itineraries")
@CrossOrigin(origins = "*")
public class TravelPlanController
{

    @Autowired
    private AITravelPlanService aiTravelPlanService;

    @PostMapping("/generate")
    public ResponseEntity<TravelPlanResponse> generateTravelPlan(@RequestBody TravelPlanRequest request)
    {
        TravelPlanResponse response = aiTravelPlanService.generateTravelPlan(request);
        return ResponseEntity.ok(response);
    }
} 