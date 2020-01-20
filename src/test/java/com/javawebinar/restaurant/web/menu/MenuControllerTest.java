package com.javawebinar.restaurant.web.menu;

import com.javawebinar.restaurant.MenuTestData;
import com.javawebinar.restaurant.TestUtil;
import com.javawebinar.restaurant.model.Menu;
import com.javawebinar.restaurant.service.MenuService;
import com.javawebinar.restaurant.util.exception.NotFoundException;
import com.javawebinar.restaurant.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.javawebinar.restaurant.MenuTestData.*;
import static com.javawebinar.restaurant.RestaurantTestData.*;
import static com.javawebinar.restaurant.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MenuControllerTest extends AbstractControllerTest {

    @Autowired
    MenuService menuService;

    public MenuControllerTest() {
        super(MenuController.REST_URL);
    }

    @Test
    void create() throws Exception {
        Menu newMenu = MenuTestData.getNew();
        ResultActions action = perform(doPost("/restaurants/" + RESTAURANT1_ID + "/menu")
                .jsonBody(newMenu).basicAuth(ADMIN));

        Menu created = TestUtil.readFromJson(action, Menu.class);
        Integer newId = created.getId();
        newMenu.setId(newId);
        MENU_MATCHERS.assertMatch(created, newMenu);
        MENU_MATCHERS.assertMatch(menuService.getMenu(newId), newMenu);
    }

    @Test
    void createMenuForbidden() throws Exception {
        Menu newMenu = MenuTestData.getNew();
        perform(doPost("/restaurants/" + RESTAURANT1.getId() + "/menu")
                .jsonBody(newMenu)
                .basicAuth(USER3))
                .andExpect(status().isForbidden());
    }

    @Test
    void updateMenu() throws Exception {
        Menu updateMenu = MenuTestData.getUpdated();
        perform(doPut("menus/" + MENU1_ID)
                .jsonBody(updateMenu).basicAuth(ADMIN));
        MENU_MATCHERS.assertMatch(menuService.getMenu(MENU1_ID), updateMenu);
    }

    @Test
    void updateMenuNotFound() throws Exception {
        Menu updateMenu = MenuTestData.getUpdated();
        perform(doPut("menus/" + 1).basicAuth(ADMIN)
                .jsonBody(updateMenu))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateMenuForbidden() throws Exception {
        Menu updateMenu = MenuTestData.getUpdated();
        perform(doPut("menus/" + MENU1_ID)
                .jsonBody(updateMenu)
                .auth(USER3))
                .andExpect(status().isForbidden());
    }

    @Test
    void getMenusWithRestaurantAndCourses() throws Exception {
        perform(doGet("/todayRestaurants").basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":100005,\"name\":\"Domino`s pizza\",\"menu\":{\"id\":100008,\"name\":\"Lunch\",\"dateTime\":\"2020-01-21\",\"restaurant\":{\"id\":100005,\"name\":\"Domino`s pizza\"},\"courses\":[{\"id\":100012,\"name\":\"Pizza Margarita\",\"price\":120},{\"id\":100011,\"name\":\"Pizza Pepperoni\",\"price\":100},{\"id\":100013,\"name\":\"Salad\",\"price\":40}]}},{\"id\":100006,\"name\":\"McDonald`s\",\"menu\":{\"id\":100009,\"name\":\"Business lunch\",\"dateTime\":\"2020-01-21\",\"restaurant\":{\"id\":100006,\"name\":\"McDonald`s\"},\"courses\":[{\"id\":100015,\"name\":\"Big Mac\",\"price\":60},{\"id\":100014,\"name\":\"Сheeseburger\",\"price\":50}]}},{\"id\":100007,\"name\":\"KFC\",\"menu\":{\"id\":100010,\"name\":\"Afternoon Snack\",\"dateTime\":\"2020-01-21\",\"restaurant\":{\"id\":100007,\"name\":\"KFC\"},\"courses\":[{\"id\":100016,\"name\":\"Hot-Dog\",\"price\":40},{\"id\":100017,\"name\":\"Longer Cheese\",\"price\":50}]}}]"));
    }

    @Test
    void getAllMenusForRestaurant() throws Exception {
        perform(doGet("/restaurants/" + RESTAURANT1_ID + "/menus").basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andExpect(MENU_MATCHERS.contentJson(MenuTestData.menuListForRestaurant));
    }

    @Test
    void deleteMenu() throws Exception {
        perform(doDelete("/menus/" + MENU1_ID).basicAuth(ADMIN))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> menuService.getMenu(MENU1_ID));
    }

    @Test
    void deleteMenuNotFound() throws Exception {
        perform(doDelete("/menus/" + 1).basicAuth(ADMIN))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deleteMenuForbidden() throws Exception {
        perform(doDelete("/menus/" + MENU1_ID).basicAuth(USER3))
                .andExpect(status().isForbidden());
    }

    @Test
    void getMenuNotFound() throws Exception {
        perform(doGet("/menus/" + 1).basicAuth(ADMIN))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getMenu() throws Exception {
        perform(doGet("/menus/" + MENU1_ID).basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andExpect(MENU_MATCHERS.contentJson(MENU1))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        Menu expected = new Menu(null, "Lunch", LocalDate.now());
        perform(doPost("/restaurants/" + RESTAURANT1_ID + "/menu").jsonBody(expected).basicAuth(ADMIN))
                .andExpect(status().isConflict());

    }
}