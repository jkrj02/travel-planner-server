package com.example.travelplanner.service;

import com.example.travelplanner.dto.TravelPlanRequest;
import com.example.travelplanner.dto.TravelPlanResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.charset.StandardCharsets;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;

@Service
public class AITravelPlanService {
    private static final Logger logger = LoggerFactory.getLogger(AITravelPlanService.class);

    @Value("${aliyun.api.key}")
    private String apiKey;

    @Value("${aliyun.api.url}")
    private String apiUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        logger.info("API URL: {}", apiUrl);
        logger.info("API Key: {}", apiKey);
    }

    public TravelPlanResponse generateTravelPlan(TravelPlanRequest request) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpRequest = new HttpPost(apiUrl);
            
            httpRequest.setHeader("Content-Type", "application/json");
            httpRequest.setHeader("Authorization", "Bearer " + apiKey);

            String prompt = buildPrompt(request);
            String requestBody = buildRequestBody(prompt);
            httpRequest.setEntity(new StringEntity(requestBody, StandardCharsets.UTF_8));

            return client.execute(httpRequest, response -> {
                String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                logger.info("Raw Response: {}", responseBody);
                return parseAIResponse(responseBody);
            });
        } catch (IOException e) {
            logger.error("HTTP request failed: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to generate travel plan", e);
        }
    }

    private String buildPrompt(TravelPlanRequest request) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("你是一个专业的旅游规划师，请为以下旅行生成详细的行程计划：\n");
        prompt.append("行程标题：").append(request.getTitle()).append("\n");
        prompt.append("目的地ID列表：").append(request.getDestinations()).append("\n");
        prompt.append("开始日期：").append(request.getStartDate()).append("\n");
        prompt.append("结束日期：").append(request.getEndDate()).append("\n");
        prompt.append("持续天数：").append(request.getDuration()).append("天\n");
        prompt.append("总预算：").append(request.getTotalBudget()).append("\n");
        
        if (request.getPreferences() != null) {
            TravelPlanRequest.Preferences prefs = request.getPreferences();
            prompt.append("行程节奏：").append(prefs.getPacePreference()).append("\n");
            prompt.append("住宿类型：").append(prefs.getAccommodationType()).append("\n");
            prompt.append("交通方式：").append(prefs.getTransportationType()).append("\n");
            prompt.append("活动偏好：").append(prefs.getActivityPreferences()).append("\n");
            if (prefs.getSpecialRequirements() != null && !prefs.getSpecialRequirements().isEmpty()) {
                prompt.append("特殊需求：").append(prefs.getSpecialRequirements()).append("\n");
            }
        }

        prompt.append("\n请按照以下JSON格式返回行程计划，注意：\n");
        prompt.append("1. 时间安排要合理，考虑交通时间\n");
        prompt.append("2. 活动类型要多样化\n");
        prompt.append("3. 地点描述要具体准确\n\n");
        prompt.append("返回格式：\n");
        prompt.append("{\n");
        prompt.append("  \"itinerary\": {\n");
        prompt.append("    \"itineraryDays\": [\n");
        prompt.append("      {\n");
        prompt.append("        \"date\": \"YYYY-MM-DD\",\n");
        prompt.append("        \"activities\": [\n");
        prompt.append("          {\n");
        prompt.append("            \"title\": \"活动名称\",\n");
        prompt.append("            \"timeStart\": \"HH:MM\",\n");
        prompt.append("            \"timeEnd\": \"HH:MM\",\n");
        prompt.append("            \"location\": \"地点\",\n");
        prompt.append("            \"description\": \"活动描述\"\n");
        prompt.append("          }\n");
        prompt.append("        ]\n");
        prompt.append("      }\n");
        prompt.append("    ]\n");
        prompt.append("  }\n");
        prompt.append("}");
        
        return prompt.toString();
    }

    private String buildRequestBody(String prompt) {
        return String.format(
            "{\"model\": \"qwen-plus\", \"input\": {\"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}, \"parameters\": {\"result_format\": \"message\"}}",
            prompt.replace("\"", "\\\"").replace("\n", "\\n")
        );
    }

    private TravelPlanResponse parseAIResponse(String response) throws IOException {
        logger.info("Parsing AI response: {}", response);
        
        if (response == null || response.trim().isEmpty()) {
            throw new IOException("Empty response from AI service");
        }

        try {
            // 解析通义千问的响应格式
            JsonNode rootNode = objectMapper.readTree(response);
            logger.info("Response JSON structure: {}", rootNode.toPrettyString());
            
            JsonNode outputNode = rootNode.path("output");
            if (outputNode.isMissingNode()) {
                logger.error("Missing 'output' node in response");
                throw new IOException("Missing 'output' node in AI response");
            }
            
            JsonNode choicesNode = outputNode.path("choices");
            if (choicesNode.isMissingNode() || !choicesNode.isArray() || choicesNode.size() == 0) {
                logger.error("Missing or empty 'choices' array in output");
                throw new IOException("Missing or empty 'choices' array in AI response");
            }
            
            JsonNode firstChoice = choicesNode.get(0);
            JsonNode messageNode = firstChoice.path("message");
            if (messageNode.isMissingNode()) {
                logger.error("Missing 'message' node in choice");
                throw new IOException("Missing 'message' node in AI response");
            }
            
            JsonNode contentNode = messageNode.path("content");
            if (contentNode.isMissingNode()) {
                logger.error("Missing 'content' node in message");
                throw new IOException("Missing 'content' node in AI response");
            }
            
            String content = contentNode.asText();
            logger.info("Extracted content: {}", content);
            
            if (content == null || content.trim().isEmpty()) {
                throw new IOException("Empty content in AI response");
            }
            
            // 从content中提取JSON
            Pattern pattern = Pattern.compile("\\{[\\s\\S]*\\}");
            Matcher matcher = pattern.matcher(content);
            
            if (matcher.find()) {
                String planJson = matcher.group();
                logger.info("Extracted plan JSON: {}", planJson);
                return objectMapper.readValue(planJson, TravelPlanResponse.class);
            } else {
                logger.error("No JSON found in content: {}", content);
                throw new IOException("No JSON found in AI response content");
            }
        } catch (Exception e) {
            logger.error("Error parsing AI response: {}", e.getMessage(), e);
            throw new IOException("Error parsing AI response: " + e.getMessage(), e);
        }
    }
} 