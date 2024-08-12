package com.bikash.repository;

import com.bikash.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant , Long>{

    Restaurant findByOwnerId(Long userId);

    @Query("SELECT r FROM Restaurant r WHERE lower(r.name) LIKE lower(concat('%' , :query , '%')) OR lower(r.cuisineType) LIKE lower(concat('%' , :query, '%')) ")
    List<Restaurant> findBySearchQuery(String query);


}
