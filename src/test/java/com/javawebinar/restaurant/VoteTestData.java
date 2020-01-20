package com.javawebinar.restaurant;

import com.javawebinar.restaurant.model.Vote;

import java.time.LocalDate;
import java.util.List;

import static com.javawebinar.restaurant.RestaurantTestData.RESTAURANT2;
import static com.javawebinar.restaurant.UserTestData.USER2;
import static com.javawebinar.restaurant.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {

    public static final int VOTE1_ID = START_SEQ + 18;

    public static final Vote VOTE2 = new Vote(VOTE1_ID + 1, USER2, RESTAURANT2, LocalDate.now());

    public static final TestMatchers<Vote> VOTE_MATCHERS = TestMatchers.useFieldsComparator(Vote.class);

    public static List<Vote> voteListForUser2 = List.of(VOTE2);
}
