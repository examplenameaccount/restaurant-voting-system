package com.javawebinar.restaurant.service;

import com.javawebinar.restaurant.model.Restaurant;
import com.javawebinar.restaurant.repository.datajpa.CrudRestaurantRepository;
import com.javawebinar.restaurant.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    CrudRestaurantRepository restaurantRepository;

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    public Restaurant get(int id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant with id " + id + " not found"));
    }

    @Transactional
    public Restaurant create(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public void update(Restaurant restaurantRequest, int id) {
        Restaurant updateRestaurant = restaurantRepository.findById(id)
                .map(restaurant -> new Restaurant(restaurantRequest.getId(), restaurantRequest.getName()))
                .orElseThrow(() -> new NotFoundException("Restaurant with id " + id + " not found"));
        restaurantRepository.save(updateRestaurant);
    }

    @Transactional
    public void delete(int id) {
        restaurantRepository.delete(restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant with id " + id + " not found")));
    }
}
