package com.example.travelplanner.repository;

import com.example.travelplanner.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    
    List<Destination> findByCountry(String country);
    
    List<Destination> findByCity(String city);
    
    List<Destination> findByRecommendedTravelStyle(String travelStyle);
    
    List<Destination> findByRecommendedBudgetLevel(String budgetLevel);
    
    @Query("SELECT d FROM Destination d WHERE d.name LIKE %:keyword% OR d.description LIKE %:keyword%")
    List<Destination> findByKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT d FROM Destination d WHERE d.recommendedTravelStyle = :travelStyle AND d.recommendedBudgetLevel = :budgetLevel")
    List<Destination> findByTravelStyleAndBudgetLevel(@Param("travelStyle") String travelStyle, @Param("budgetLevel") String budgetLevel);
} 