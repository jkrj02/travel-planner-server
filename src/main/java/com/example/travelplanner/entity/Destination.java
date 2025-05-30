package com.example.travelplanner.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "destinations")
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String country;
    private String city;
    private String province;

    @Column(name = "image_url")
    private String imageUrl;

    // 地理坐标
    private BigDecimal latitude;
    private BigDecimal longitude;

    // 推荐等级 (1-5)
    @Column(name = "rating")
    private Integer rating;

    // 分类标签
    @ElementCollection
    @CollectionTable(name = "destination_tags", joinColumns = @JoinColumn(name = "destination_id"))
    @Column(name = "tag")
    private List<String> tags;

    // 推荐旅行风格
    @Column(name = "recommended_travel_style")
    private String recommendedTravelStyle;

    // 推荐预算等级
    @Column(name = "recommended_budget_level")
    private String recommendedBudgetLevel;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 默认构造函数
    public Destination() {}

    // 构造函数
    public Destination(String name, String description, String country, String city) {
        this.name = name;
        this.description = description;
        this.country = country;
        this.city = city;
        this.rating = 4; // 默认评级
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getRecommendedTravelStyle() {
        return recommendedTravelStyle;
    }

    public void setRecommendedTravelStyle(String recommendedTravelStyle) {
        this.recommendedTravelStyle = recommendedTravelStyle;
    }

    public String getRecommendedBudgetLevel() {
        return recommendedBudgetLevel;
    }

    public void setRecommendedBudgetLevel(String recommendedBudgetLevel) {
        this.recommendedBudgetLevel = recommendedBudgetLevel;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
} 