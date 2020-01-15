package com.javawebinar.restaurant.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "local_date"}, name = "votes_unique_user_id_local_date_idx")})
public class Vote extends AbstractBaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    Restaurant restaurant;

    @Column(name = "local_date")
    private LocalDate localDate;

    public Vote() {
    }

    public Vote(Integer id, User user, Restaurant restaurant, LocalDate localDate) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.localDate = localDate;
    }

    public Vote(User user, Restaurant restaurant, LocalDate localDate) {
        this.user = user;
        this.restaurant = restaurant;
        this.localDate = localDate;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}
