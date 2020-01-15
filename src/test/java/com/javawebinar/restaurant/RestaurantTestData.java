package com.javawebinar.restaurant;

import com.javawebinar.restaurant.model.Restaurant;

import java.util.List;

import static com.javawebinar.restaurant.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final int RESTAURANT1_ID = START_SEQ + 5;

    public static final Restaurant RESTAURANT1 = new Restaurant(RESTAURANT1_ID, "Domino`s pizza");
    public static final Restaurant RESTAURANT2 = new Restaurant(RESTAURANT1_ID + 1, "McDonald`s");
    public static final Restaurant RESTAURANT3 = new Restaurant(RESTAURANT1_ID + 2, "KFC");

    public static Restaurant getNew() {
        return new Restaurant(null, "New");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(RESTAURANT1);
        updated.setName("UpdatedName");
        return updated;
    }

    public static List<Restaurant> restaurantList = List.of(RESTAURANT1, RESTAURANT2, RESTAURANT3);

    public static TestMatchers<Restaurant> RESTAURANT_MATCHERS = TestMatchers.useFieldsComparator(Restaurant.class, "votes", "menu");

}
