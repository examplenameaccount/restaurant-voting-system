package com.javawebinar.restaurant;

import com.javawebinar.restaurant.model.Menu;
import com.javawebinar.restaurant.model.Restaurant;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.javawebinar.restaurant.RestaurantTestData.*;
import static com.javawebinar.restaurant.model.AbstractBaseEntity.START_SEQ;

public class MenuTestData extends AbstractTestData {
    public static final int MENU1_ID = START_SEQ + 8;

    public static final Menu MENU1 = new Menu(MENU1_ID, "Lunch", TODAY_DATE, RESTAURANT1);
    public static final Menu MENU2 = new Menu(MENU1_ID + 1, "Business lunch", TODAY_DATE, RESTAURANT2);
    public static final Menu MENU3 = new Menu(MENU1_ID + 2, "Afternoon Snack", TODAY_DATE, RESTAURANT3);

    public static Menu getNew() {
        return new Menu(null, "New", LocalDate.now().plus(10, ChronoUnit.DAYS));
    }

    public static Menu getUpdated() {
        Menu updated = new Menu(MENU1);
        updated.setName("UpdatedName");
        updated.setMenuDate(LocalDate.now().plus(10, ChronoUnit.DAYS));
        return updated;
    }

    public static final TestMatchers<Menu> MENU_MATCHERS = TestMatchers.useFieldsComparator(Menu.class, "restaurant");
    public static List<Menu> menuListForRestaurant = List.of(MENU1);
}
