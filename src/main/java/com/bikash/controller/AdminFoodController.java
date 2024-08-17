package com.bikash.controller;

import com.bikash.model.Food;
import com.bikash.model.Restaurant;
import com.bikash.model.User;
import com.bikash.request.CreateFoodRequest;
import com.bikash.response.MessageResponse;
import com.bikash.service.FoodService;
import com.bikash.service.RestaurantService;
import com.bikash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("")
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
                                           @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());
        Food food = foodService.createFood(req , req.getCategory(), restaurant);

        return new ResponseEntity<>(food , HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@RequestHeader("Authorization") String jwt,
                                                      @PathVariable Long id) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
       foodService.deleteFood(id);

       MessageResponse res = new MessageResponse();
       res.setMessage("Successfully deleted food");
        return new ResponseEntity<>(res , HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvailabilityStatus(@PathVariable Long id,
                                                             @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailability(id);

        return new ResponseEntity<>(food , HttpStatus.CREATED);
    }

}
