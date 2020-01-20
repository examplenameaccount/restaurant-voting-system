package com.javawebinar.restaurant.to;

import com.javawebinar.restaurant.model.AbstractNamedEntity;
import com.javawebinar.restaurant.model.Menu;

public class RestaurantTo extends AbstractNamedEntity {

    private Menu menu;

    public RestaurantTo(Menu m) {
        super(m.getRestaurant().getId(), m.getRestaurant().getName());
        m.setCourses(m.getCourses());
        this.menu = m;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Restaurant" + ", name='" + name + '\'' +
                " menu=" + menu +
                '}';
    }
}
