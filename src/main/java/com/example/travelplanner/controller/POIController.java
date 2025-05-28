package com.example.travelplanner.controller;

import com.example.travelplanner.dto.POIResponse;
import com.example.travelplanner.service.POIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/destination")
public class POIController
{    
    @Autowired
    private POIService poiService;

    @GetMapping("/search")
    public POIResponse searchPOIs(@RequestParam String city, @RequestParam(required = false, defaultValue = "景点") String keywords)
    {
        return poiService.searchPOIs(city, keywords);
    }
} 