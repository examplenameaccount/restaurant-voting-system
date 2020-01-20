package com.javawebinar.restaurant.to;

import com.javawebinar.restaurant.model.Restaurant;

public class VoteStatistic {
    private Restaurant restaurant;
    private Long count;

    public VoteStatistic(Restaurant restaurant, Long count) {
        this.restaurant = restaurant;
        this.count = count;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("VoteStatistic{");
        sb.append("restaurant=").append(restaurant);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
