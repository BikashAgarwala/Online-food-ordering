package com.bikash.service;

import com.bikash.model.Category;
import com.bikash.model.Food;
import com.bikash.model.Restaurant;
import com.bikash.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    public Food createFood(CreateFoodRequest req , Category category , Restaurant restaurant);

    public void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantFood(Long id ,
                                        boolean isVegetarian,
                                        boolean isNonVeg,
                                        boolean isSeasonal,
                                        String foodCategory
                                        );

    public List<Food> searchFood(String keyword);

    public Food findFoodById(Long foodId) throws Exception;

    public Food updateAvailability(Long foodId) throws Exception;

}
