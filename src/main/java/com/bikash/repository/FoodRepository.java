package com.bikash.repository;

import com.bikash.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findByRestaurantId(Long restaurantId);

    @Query("SELECT f FROM Food f WHERE f.name LIKE %:keyeord% OR f.foodCategory.name LIKE %:keword%")
    List<Food> searchFood(@Param("keyword") String keyword);

}
