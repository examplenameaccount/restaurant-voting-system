package com.javawebinar.restaurant.service;

import com.javawebinar.restaurant.RestaurantTestData;
import com.javawebinar.restaurant.model.Restaurant;
import com.javawebinar.restaurant.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.util.List;

import static com.javawebinar.restaurant.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RestaurantServiceTest extends AbstractServiceTest {
    @Autowired
    protected RestaurantService restaurantService;

    @Test
    void create() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        Restaurant created = restaurantService.create(new Restaurant(newRestaurant));
        Integer newId = created.getId();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHERS.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHERS.assertMatch(restaurantService.get(newId), newRestaurant);
    }

    @Test
    void duplicateNameCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                restaurantService.create(new Restaurant(null, "KFC")));
    }

    @Test
    void delete() throws Exception {
        restaurantService.delete(RESTAURANT1_ID);
        assertThrows(NotFoundException.class, () ->
                restaurantService.delete(RESTAURANT1_ID));
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                restaurantService.delete(1));
    }

    @Test
    void get() throws Exception {
        Restaurant restaurant = restaurantService.get(RESTAURANT1_ID);
        RESTAURANT_MATCHERS.assertMatch(restaurant, RESTAURANT1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                restaurantService.get(1));
    }

    @Test
    void update() throws Exception {
        Restaurant updated = getUpdated();
        restaurantService.update(new Restaurant(updated), RESTAURANT1_ID);
        RESTAURANT_MATCHERS.assertMatch(restaurantService.get(RESTAURANT1_ID), updated);
    }

    @Test
    void getAll() throws Exception {
        List<Restaurant> all = restaurantService.getAll();
        RESTAURANT_MATCHERS.assertMatch(all, restaurantList);
    }
}
