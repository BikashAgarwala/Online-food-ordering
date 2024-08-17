package com.bikash.service;

import com.bikash.model.Category;
import com.bikash.model.Food;
import com.bikash.model.Restaurant;
import com.bikash.repository.FoodRepository;
import com.bikash.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImp implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
        Food food = new Food();
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(req.getDescription());
        food.setName(req.getName());
        food.setIngredients(req.getIngredients());
        food.setPrice(req.getPrice());
        food.setImages(req.getImages());
        food.setSeasonable(req.isSeasonal());
        food.setVegetarian(req.isVegetarian());
        Food savedFood = foodRepository.save(food);
        restaurant.getFoods().add(savedFood);
        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);
    }

    @Override
    public List<Food> getRestaurantFood(Long id,
                                        boolean isVegetarian,
                                        boolean isNonVeg,
                                        boolean isSeasonal,
                                        String foodCategory) {
        List<Food> foods = foodRepository.findByRestaurantId(id);

        if(isVegetarian) {
            foods = filterByVegetarian(foods,isVegetarian);
        }
        if(isNonVeg) {
            foods = filterByNonVeg(foods,isNonVeg);
        }
        if(isSeasonal) {
            foods = filterBySeasonal(foods,isSeasonal);
        }
        if(foodCategory != null && !foodCategory.isEmpty()) {
            foods = filterByCategory(foods,foodCategory);
        }

        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if(food.getFoodCategory() != null){
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food ->  food.isSeasonable() == isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByNonVeg(List<Food> foods, boolean isNonVeg) {
        return foods.stream().filter(food -> !food.isVegetarian()).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(food -> food.isVegetarian() == isVegetarian).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> food = foodRepository.findById(foodId);
        if(food.isEmpty()) {
            throw new Exception("Food not found");
        }
        return food.get();
    }

    @Override
    public Food updateAvailability(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());

        return foodRepository.save(food);
    }
}
