package com.javawebinar.restaurant.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "courses", uniqueConstraints = {@UniqueConstraint(columnNames = {"menu_id", "name"}, name = "courses_unique_menu_id_name_idx")})
public class Course extends AbstractNamedEntity {
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "menu_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Menu menu;

    @Column(name = "price", nullable = false)
    private int price;


    public Course() {
    }

    public Course(Integer id, String name, Integer price) {
        super(id, name);
        this.price = price;
    }

    public Course(Integer id, String name, Integer price, Menu menu) {
        super(id, name);
        this.price = price;
        this.menu = menu;
    }

    public Course(Course course) {
        this(course.getId(), course.getName(), course.getPrice(), course.getMenu());
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
