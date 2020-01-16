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