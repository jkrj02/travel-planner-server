package com.example.travelplanner.dto;

import java.util.List;
import lombok.Data;

@Data
public class POIResponse {
    private String status;
    private String info;
    private String infocode;
    private String count;
    private List<POI> pois;

    @Data
    public static class POI {
        private String name;
        private String id;
        private String location;
        private String type;
        private String typecode;
        private String pname;
        private String cityname;
        private String adname;
        private String address;
        private String pcode;
        private String adcode;
        private String citycode;
        // business
        private String business_area;
        private String opentime_today;
        private String opentime_week;
        private String tel;
        private String tag;
        private String rating;
        private String cost;
        private String parking_type;
        private String alias;        
    }
} 