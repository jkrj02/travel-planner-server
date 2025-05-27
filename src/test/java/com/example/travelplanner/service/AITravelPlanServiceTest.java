package com.example.travelplanner.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.travelplanner.dto.TravelPlanRequest;
import com.example.travelplanner.dto.TravelPlanResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;

@SpringBootTest
public class AITravelPlanServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(SimpleServiceTest.class);

    @Autowired
    private AITravelPlanService service;

    @Test
    void testTravelPlan() {
        try {
            // 创建请求对象
            TravelPlanRequest request = new TravelPlanRequest();
            request.setTitle("北京三日游");
            request.setDestinations(Arrays.asList(1L));
            request.setStartDate("2025-05-28");
            request.setEndDate("2025-05-30");
            request.setDuration(3);
            request.setTotalBudget(5000.0);

            // 设置偏好
            TravelPlanRequest.Preferences preferences = new TravelPlanRequest.Preferences();
            preferences.setPacePreference("moderate");
            preferences.setAccommodationType("mid-range");
            preferences.setTransportationType("public");
            preferences.setActivityPreferences(Arrays.asList("food", "sightseeing", "shopping"));
            preferences.setSpecialRequirements("不喜欢博物馆，喜欢吃，喜欢citywalk，想去环球影城");
            request.setPreferences(preferences);

            // 生成行程
            TravelPlanResponse response = service.generateTravelPlan(request);
            
            // 打印结果
            logger.info("生成的行程计划：");
            logger.info("行程标题：{}", request.getTitle());
            logger.info("目的地ID：{}", request.getDestinations());
            logger.info("开始日期：{}", request.getStartDate());
            logger.info("结束日期：{}", request.getEndDate());
            logger.info("持续天数：{}", request.getDuration());
            logger.info("总预算：{}", request.getTotalBudget());
            
            logger.info("每日行程：");
            response.getItinerary().getItineraryDays().forEach(day -> {
                logger.info("\n日期：{}", day.getDate());
                day.getActivities().forEach(activity -> {
                    logger.info("{} - {} ({})", activity.getTimeStart(), activity.getTimeEnd(), activity.getTitle());
                    logger.info("描述：{}", activity.getDescription());
                    logger.info("地点：{}", activity.getLocation());
                });
            });
            
        } catch (Exception e) {
            logger.error("生成行程时发生错误：{}", e.getMessage(), e);
        }
    }
} 