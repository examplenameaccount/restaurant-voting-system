package com.javawebinar.restaurant.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"date_time", "restaurant_id"}, name = "menus_unique_date_time_id_name_idx")})
public class Menu extends AbstractNamedEntity {
    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDate dateTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menu")
    @JsonManagedReference
    private List<Course> courses = new ArrayList<>();

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

    public Menu(Integer id, String name, LocalDate dateTime, List<Course> courses) {
        super(id, name);
        this.dateTime = dateTime;
        this.courses = courses;
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

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "dateTime=" + dateTime +
                "} " + super.toString();
    }
}
