package com.bikash.controller;

import com.bikash.model.Food;
import com.bikash.model.User;
import com.bikash.service.FoodService;
import com.bikash.service.RestaurantService;
import com.bikash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String keyword,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> food = foodService.searchFood(keyword);

        return new ResponseEntity<>(food , HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(@PathVariable Long restaurantId,
                                                        @RequestParam boolean vegetarian,
                                                        @RequestParam boolean seasonal,
                                                        @RequestParam boolean nonVeg,
                                                 @RequestParam(required = false) String foodCategory,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> food = foodService.getRestaurantFood(restaurantId , vegetarian , nonVeg , seasonal , foodCategory );

        return new ResponseEntity<>(food , HttpStatus.OK);
    }

}
