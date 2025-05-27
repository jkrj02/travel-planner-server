package com.example.travelplanner.service;

import com.example.travelplanner.dto.POIResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class AMapService {
    private static final Logger logger = LoggerFactory.getLogger(AMapService.class);

    @Value("${amap.api.url}")
    private String apiUrl;

    @Value("${amap.api.key}")
    private String apiKey;

    // // 高德地图所有POI类型
    // private static final String types = "010000 | 020000 | 030000 | 040000 | 050000 " +
    //                                     "| 060000 | 070000 | 080000 | 090000 | 100000 " +
    //                                     "| 110000 | 120000 | 130000 | 140000 | 150000 " +
    //                                     "| 160000 | 170000 | 180000 | 190000 | 200000 " +
    //                                     "| 220000 | 970000 | 980000 | 990000" ;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public POIResponse searchPOIs(String city, String keywords) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // URL编码拼接
            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
            String encodedKeywords = URLEncoder.encode(keywords, StandardCharsets.UTF_8);
            
            String url = String.format("%s?key=%s&keywords=%s&region=%s&output=json&page_size=25&page_num=1%showfields=business",
                    apiUrl, apiKey, encodedKeywords, encodedCity);

            HttpGet httpRequest = new HttpGet(url);

            return client.execute(httpRequest, response -> {
                String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                logger.info("AMap POI 2.0 API Response: {}", responseBody);
                return objectMapper.readValue(responseBody, POIResponse.class);
            });
        } catch (java.net.SocketTimeoutException e) {
            logger.error("Request timeout: {}", e.getMessage());
            throw new RuntimeException("Request timeout while searching", e);
        } catch (IOException e) {
            logger.error("Failed to search POIs: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to search", e);
        }
    }
} 