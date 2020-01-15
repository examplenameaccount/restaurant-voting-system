package com.javawebinar.restaurant.service;

import com.javawebinar.restaurant.MenuTestData;
import com.javawebinar.restaurant.RestaurantTestData;
import com.javawebinar.restaurant.model.Menu;
import com.javawebinar.restaurant.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.time.LocalDate;
import java.util.List;

import static com.javawebinar.restaurant.MenuTestData.*;
import static com.javawebinar.restaurant.RestaurantTestData.RESTAURANT1_ID;
import static com.javawebinar.restaurant.RestaurantTestData.RESTAURANT2;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MenuServiceTest extends AbstractServiceTest {
    @Autowired
    MenuService menuService;

    @Test
    void create() throws Exception {
        Menu newMenu = MenuTestData.getNew();
        Menu created = menuService.createMenu(RESTAURANT2.getId(), new Menu(newMenu));
        Integer newId = created.getId();
        newMenu.setId(newId);
        MENU_MATCHERS.assertMatch(created, newMenu);
        MENU_MATCHERS.assertMatch(menuService.getMenu(newId), newMenu);
    }

    @Test
    void duplicateDateNameCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                menuService.createMenu(RESTAURANT1_ID, new Menu(null, "Lunch", LocalDate.now())));
    }

    @Test
    void delete() throws Exception {
        menuService.deleteMenu(MENU1_ID);
        assertThrows(NotFoundException.class, () ->
                menuService.deleteMenu(MENU1_ID));
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                menuService.deleteMenu(1));
    }

    @Test
    void get() throws Exception {
        Menu menu = menuService.getMenu(MENU1_ID);
        MENU_MATCHERS.assertMatch(menu, MENU1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                menuService.getMenu(1));
    }

    @Test
    void update() throws Exception {
        Menu updated = getUpdated();
        menuService.updateMenu(MENU1_ID, new Menu(updated));
        MENU_MATCHERS.assertMatch(menuService.getMenu(MENU1_ID), updated);
    }

    @Test
    void getAll() throws Exception {
        List<Menu> all = menuService.getAllMenus(RESTAURANT1_ID);
        MENU_MATCHERS.assertMatch(all, menuListForRestaurant);
    }
}
