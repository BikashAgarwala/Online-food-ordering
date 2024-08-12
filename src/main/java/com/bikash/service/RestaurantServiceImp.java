package com.bikash.service;

import com.bikash.model.Address;
import com.bikash.model.Restaurant;
import com.bikash.model.User;
import com.bikash.repository.AddressRepository;
import com.bikash.repository.RestaurantRepository;
import com.bikash.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImp implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserService userService;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {

        Address address = addressRepository.save(req.getAddress());
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setImages(req.getImages());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);


        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);

        if(restaurant.getCuisineType()!=null){
            restaurant.setCuisineType(updateRestaurant.getCuisineType());
        }
        if(restaurant.getDescription()!=null){
            restaurant.setDescription(updateRestaurant.getDescription());
        }
        if(restaurant.getName()!=null){
            restaurant.setName(updateRestaurant.getName());
        }
        if(restaurant.getOpeningHours()!=null){
            restaurant.setOpeningHours(updateRestaurant.getOpeningHours());
        }
        if(restaurant.getImages() != null){
            restaurant.setImages(updateRestaurant.getImages());
        }
        if(restaurant.getContactInformation()!=null){
            restaurant.setContactInformation(updateRestaurant.getContactInformation());
        }
        if(restaurant.getAddress()!=null){
            restaurant.setAddress(updateRestaurant.getAddress());
        }

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    public Restaurant findRestaurantById(Long restaurantId) throws Exception {
         Optional<Restaurant> opt = restaurantRepository.findById(restaurantId);
         if(opt.isEmpty()){
             throw new Exception("Restaurant not found");
         }
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantById(Long id) throws Exception {
        return null;
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if(restaurant == null){
            throw new Exception("Restaurant not found with id " + userId);
        }
        return restaurant;
    }

    @Override
    public Restaurant addToFavorites(Long restaurantId, User user) throws Exception {
        return null;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        return null;
    }
}
