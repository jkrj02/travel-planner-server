package com.example.travelplanner.service;

import com.example.travelplanner.dto.POIResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class POIServiceTest
{
    private static final Logger logger = LoggerFactory.getLogger(POIServiceTest.class);

    @Autowired
    private POIService poiService;

    @Test
    void testSearchPOIs()
    {
        try
        {
            // 正确输入
            POIResponse response = poiService.searchPOIs("北京", "美食");
            
            // 打印结果
            logger.info("搜索结果：");
            response.getPois().forEach(poi -> {
                logger.info("景点名称：{}", poi.getName());
                logger.info("地址：{}", poi.getAddress());
                logger.info("位置：{}", poi.getLocation());
                if(poi.getRating() != null)
                {
                    logger.info("评分：{}", poi.getRating());
                }
                if(poi.getOpentime_today() != null)
                {
                    logger.info("今日营业时间：{}", poi.getOpentime_today());
                }
                if(poi.getOpentime_week() != null)
                {
                    logger.info("本周营业时间：{}", poi.getOpentime_week());
                }
            });
            
        }
        catch (Exception e)
        {
            logger.error("搜索POI时发生错误：{}", e.getMessage(), e);
            fail("搜索POI失败", e);
        }
    }
    
    @Test
    void testSearchPOIsWithEmptyKeywords()
    {
        try
        {
            // 空keyword输入
            POIResponse response = poiService.searchPOIs("北京", "");
            
            // 打印结果
            logger.info("搜索结果：");
            response.getPois().forEach(poi -> {
                logger.info("景点名称：{}", poi.getName());
                logger.info("地址：{}", poi.getAddress());
                logger.info("位置：{}", poi.getLocation());
                if(poi.getRating() != null)
                {
                    logger.info("评分：{}", poi.getRating());
                }
                if(poi.getOpentime_today() != null)
                {
                    logger.info("今日营业时间：{}", poi.getOpentime_today());
                }
                if(poi.getOpentime_week() != null)
                {
                    logger.info("本周营业时间：{}", poi.getOpentime_week());
                }
            });
            
        }
        catch (Exception e)
        {
            logger.error("搜索POI时发生错误：{}", e.getMessage(), e);
            fail("搜索POI失败", e);
        }
    }

    @Test
    void testSearchPOIsWithNullCity()
    {
        try
        {
            // 错误输入
            POIResponse response = poiService.searchPOIs(null, "");
            
            // 输出相应
            logger.info(response.getStatus());
            // 验证响应
            assertNotNull(response);
            // 验证返回的POI列表为空
            assertTrue(response.getPois() == null || response.getPois().isEmpty());
            
        }
        catch (Exception e)
        {
            logger.error("搜索POI时发生错误：{}", e.getMessage(), e);
            fail("搜索POI失败", e);
        }
    }
}