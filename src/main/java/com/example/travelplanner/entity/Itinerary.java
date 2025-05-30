package com.example.travelplanner.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "itineraries")
public class Itinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "total_budget")
    private BigDecimal totalBudget;

    @Column(name = "estimated_cost")
    private BigDecimal estimatedCost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItineraryStatus status = ItineraryStatus.DRAFT;

    // 行程天数详情（JSON格式存储）
    @Column(name = "daily_plans", columnDefinition = "TEXT")
    private String dailyPlans;

    // 目的地列表
    @ManyToMany
    @JoinTable(
        name = "itinerary_destinations",
        joinColumns = @JoinColumn(name = "itinerary_id"),
        inverseJoinColumns = @JoinColumn(name = "destination_id")
    )
    private List<Destination> destinations;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 默认构造函数
    public Itinerary() {}

    // 构造函数
    public Itinerary(String title, String description, User user) {
        this.title = title;
        this.description = description;
        this.user = user;
        this.status = ItineraryStatus.DRAFT;
    }

    // 行程状态枚举
    public enum ItineraryStatus {
        DRAFT("草稿"),
        CONFIRMED("已确认"),
        IN_PROGRESS("进行中"),
        COMPLETED("已完成"),
        CANCELLED("已取消");

        private final String displayName;

        ItineraryStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(BigDecimal totalBudget) {
        this.totalBudget = totalBudget;
    }

    public BigDecimal getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(BigDecimal estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public ItineraryStatus getStatus() {
        return status;
    }

    public void setStatus(ItineraryStatus status) {
        this.status = status;
    }

    public String getDailyPlans() {
        return dailyPlans;
    }

    public void setDailyPlans(String dailyPlans) {
        this.dailyPlans = dailyPlans;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<Destination> destinations) {
        this.destinations = destinations;
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