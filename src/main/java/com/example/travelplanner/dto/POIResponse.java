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
        private String parent;
        private String address;
        private String distance;
        private String pcode;
        private String adcode;
        private String pname;
        private String cityname;
        private String type;
        private String typecode;
        private String adname;
        private String citycode;
        private String name;
        private String location;
        private String id;
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