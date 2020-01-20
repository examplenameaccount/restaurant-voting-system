package com.javawebinar.restaurant.web.restaurant;

import com.javawebinar.restaurant.MenuTestData;
import com.javawebinar.restaurant.RestaurantTestData;
import com.javawebinar.restaurant.TestUtil;
import com.javawebinar.restaurant.model.Menu;
import com.javawebinar.restaurant.model.Restaurant;
import com.javawebinar.restaurant.service.RestaurantService;
import com.javawebinar.restaurant.util.exception.NotFoundException;
import com.javawebinar.restaurant.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static com.javawebinar.restaurant.RestaurantTestData.*;
import static com.javawebinar.restaurant.UserTestData.ADMIN;
import static com.javawebinar.restaurant.UserTestData.USER1;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantControllerTest extends AbstractControllerTest {
    @Autowired
    private RestaurantService restaurantService;

    public RestaurantControllerTest() {
        super(RestaurantController.REST_URL);
    }

    @Test
    void getAll() throws Exception {
        perform(doGet().basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andExpect(RESTAURANT_MATCHERS.contentJson(RestaurantTestData.restaurantList));
    }
    @Test
    void get() throws Exception {
        perform(doGet(RESTAURANT1.getId()).basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andExpect(RESTAURANT_MATCHERS.contentJson(RESTAURANT1))
                .andDo(print());
    }

    @Test
    void getUnauth() throws Exception {
        perform(doGet(RESTAURANT1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(doGet(1).basicAuth(ADMIN))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void create() throws Exception {
        Restaurant newRestaurant = getNew();

        ResultActions action = perform(doPost().jsonBody(newRestaurant).basicAuth(ADMIN));

        Restaurant created = TestUtil.readFromJson(action, Restaurant.class);
        Integer newId = created.getId();
        newRestaurant.setId(newId);

        RESTAURANT_MATCHERS.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHERS.assertMatch(restaurantService.get(newId), newRestaurant);
    }

    @Test
    void createForbidden() throws Exception {
        Restaurant newRestaurant = getNew();

        perform(doPost().jsonBody(newRestaurant).basicAuth(USER1))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    void update() throws Exception {
        Restaurant updated = getUpdated();
        perform(doPut(RestaurantTestData.RESTAURANT1_ID).jsonBody(updated).basicAuth(ADMIN))
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHERS.assertMatch(restaurantService.get(RESTAURANT1_ID), updated);
    }

    @Test
    void updateNotFound() throws Exception {
        Restaurant updated = getUpdated();
        perform(doPut(1).jsonBody(updated).basicAuth(ADMIN))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateForbidden() throws Exception {
        Restaurant updated = getUpdated();
        perform(doPut(RestaurantTestData.RESTAURANT1_ID).jsonBody(updated).basicAuth(USER1))
                .andExpect(status().isForbidden());
    }

    @Test
    void delete() throws Exception {
        perform(doDelete(RESTAURANT1_ID).basicAuth(ADMIN))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> restaurantService.get(RESTAURANT1_ID));
    }

    @Test
    void deleteForbidden() throws Exception {
        perform(doDelete(RESTAURANT1_ID).basicAuth(USER1))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(doDelete(1).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}