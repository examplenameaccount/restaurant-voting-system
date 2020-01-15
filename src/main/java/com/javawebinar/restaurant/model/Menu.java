package com.javawebinar.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"date_time", "restaurant_id"}, name = "menus_unique_date_time_id_name_idx")})
public class Menu extends AbstractNamedEntity {
    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDate dateTime;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public Menu() {
    }

    public Menu(Menu m) {
        this(m.getId(), m.getName(), m.getMenuDate(), m.getRestaurant());
    }

    public Menu(Integer id, String name, LocalDate menuDate) {
        super(id, name);
        this.dateTime = menuDate;
    }

    public Menu(Integer id, String name, LocalDate menuDate, Restaurant restaurant) {
        super(id, name);
        this.dateTime = menuDate;
        this.restaurant = restaurant;
    }

    public LocalDate getMenuDate() {
        return dateTime;
    }

    public void setMenuDate(LocalDate menuDate) {
        this.dateTime = menuDate;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
