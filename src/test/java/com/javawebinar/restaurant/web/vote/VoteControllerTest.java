package com.javawebinar.restaurant.web.vote;

import com.javawebinar.restaurant.RestaurantTestData;
import com.javawebinar.restaurant.service.VoteService;
import com.javawebinar.restaurant.util.TimeUtil;
import com.javawebinar.restaurant.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.javawebinar.restaurant.RestaurantTestData.RESTAURANT1;
import static com.javawebinar.restaurant.UserTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteController.REST_URL;

    @Autowired
    private VoteService voteService;

    public VoteControllerTest() {
        super(REST_URL);
    }

    @Test
    public void testUnAuth() throws Exception {
        perform(doPost("/vote/restaurants/" + RESTAURANT1.getId()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testCreateVoteBefore11() throws Exception {
        TimeUtil.useFixedClockAt(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)));

        perform(doPost("/vote/restaurants/" + RESTAURANT1.getId()).basicAuth(USER4))
                .andExpect(status().isCreated())
                .andExpect(RestaurantTestData.RESTAURANT_MATCHERS.contentJson(RESTAURANT1));
    }

    @Test
    public void testUpdateVoteBefore11() throws Exception {
        TimeUtil.useFixedClockAt(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)));

        perform(doPost("/vote/restaurants/" + RESTAURANT1.getId()).basicAuth(USER1))
                .andExpect(status().isOk())
                .andExpect(RestaurantTestData.RESTAURANT_MATCHERS.contentJson(RESTAURANT1));

    }

    @Test
    public void testCreateVoteAfter11() throws Exception {
        TimeUtil.useFixedClockAt(LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 1)));

        perform(doPost("/vote/restaurants/" + RESTAURANT1.getId()).basicAuth(USER4))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(RestaurantTestData.RESTAURANT_MATCHERS.contentJson(RESTAURANT1));
    }

    @Test
    public void testUpdateVoteAfter11() throws Exception {
        TimeUtil.useFixedClockAt(LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 1)));

        perform(doPost("/vote/restaurants/" + RESTAURANT1.getId()).basicAuth(USER3))
                .andExpect(status().isConflict());
    }

    @Test
    public void voteNotFound() throws Exception {
        perform(doPost("/vote/restaurants/" + 1).basicAuth(USER3))
                .andExpect(status().isUnprocessableEntity());
    }
}