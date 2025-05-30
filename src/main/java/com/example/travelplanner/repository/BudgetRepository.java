package com.example.travelplanner.repository;

import com.example.travelplanner.entity.Budget;
import com.example.travelplanner.entity.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    
    Optional<Budget> findByItinerary(Itinerary itinerary);
    
    Optional<Budget> findByItineraryId(Long itineraryId);
    
    boolean existsByItinerary(Itinerary itinerary);
} 