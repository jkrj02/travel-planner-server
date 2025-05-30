package com.example.travelplanner.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "budgets")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "itinerary_id", nullable = false)
    private Itinerary itinerary;

    @Column(name = "total_budget", nullable = false)
    private BigDecimal totalBudget;

    @Column(name = "accommodation_budget")
    private BigDecimal accommodationBudget;

    @Column(name = "transportation_budget")
    private BigDecimal transportationBudget;

    @Column(name = "food_budget")
    private BigDecimal foodBudget;

    @Column(name = "activities_budget")
    private BigDecimal activitiesBudget;

    @Column(name = "shopping_budget")
    private BigDecimal shoppingBudget;

    @Column(name = "emergency_budget")
    private BigDecimal emergencyBudget;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Expense> expenses = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 默认构造函数
    public Budget() {}

    // 构造函数
    public Budget(Itinerary itinerary, BigDecimal totalBudget) {
        this.itinerary = itinerary;
        this.totalBudget = totalBudget;
    }

    // 计算已花费总额
    public BigDecimal getTotalSpent() {
        return expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // 计算剩余预算
    public BigDecimal getRemainingBudget() {
        return totalBudget.subtract(getTotalSpent());
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public BigDecimal getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(BigDecimal totalBudget) {
        this.totalBudget = totalBudget;
    }

    public BigDecimal getAccommodationBudget() {
        return accommodationBudget;
    }

    public void setAccommodationBudget(BigDecimal accommodationBudget) {
        this.accommodationBudget = accommodationBudget;
    }

    public BigDecimal getTransportationBudget() {
        return transportationBudget;
    }

    public void setTransportationBudget(BigDecimal transportationBudget) {
        this.transportationBudget = transportationBudget;
    }

    public BigDecimal getFoodBudget() {
        return foodBudget;
    }

    public void setFoodBudget(BigDecimal foodBudget) {
        this.foodBudget = foodBudget;
    }

    public BigDecimal getActivitiesBudget() {
        return activitiesBudget;
    }

    public void setActivitiesBudget(BigDecimal activitiesBudget) {
        this.activitiesBudget = activitiesBudget;
    }

    public BigDecimal getShoppingBudget() {
        return shoppingBudget;
    }

    public void setShoppingBudget(BigDecimal shoppingBudget) {
        this.shoppingBudget = shoppingBudget;
    }

    public BigDecimal getEmergencyBudget() {
        return emergencyBudget;
    }

    public void setEmergencyBudget(BigDecimal emergencyBudget) {
        this.emergencyBudget = emergencyBudget;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
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