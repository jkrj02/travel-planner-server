package com.example.travelplanner.repository;

import com.example.travelplanner.entity.Itinerary;
import com.example.travelplanner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {
    
    List<Itinerary> findByUser(User user);
    
    List<Itinerary> findByUserOrderByCreatedAtDesc(User user);
    
    List<Itinerary> findByStatus(Itinerary.ItineraryStatus status);
    
    List<Itinerary> findByUserAndStatus(User user, Itinerary.ItineraryStatus status);
} 