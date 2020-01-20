package com.javawebinar.restaurant.web.vote;

import com.javawebinar.restaurant.model.Restaurant;
import com.javawebinar.restaurant.model.Vote;
import com.javawebinar.restaurant.service.VoteService;
import com.javawebinar.restaurant.to.VoteStatistic;
import com.javawebinar.restaurant.to.VoteTo;
import com.javawebinar.restaurant.util.TimeUtil;
import com.javawebinar.restaurant.web.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    static final String REST_URL = "/rest";

    public static boolean isBeforeElevenAM() {
        return TimeUtil.nowTime().isBefore(TimeUtil.ELEVEN_AM);
    }

    @Autowired
    VoteService voteService;

    @PostMapping("/vote/restaurants/{restaurantId}")
    public ResponseEntity<Restaurant> saveVote(@PathVariable int restaurantId) {
        int userId = SecurityUtil.authUserId();
        VoteTo voteTo = isBeforeElevenAM() ? voteService.saveOrUpdate(userId, restaurantId) :
                voteService.save(userId, restaurantId);
        return new ResponseEntity<>(voteTo.getVote().getRestaurant(),
                voteTo.isCreated() ? HttpStatus.CREATED : (isBeforeElevenAM() ? HttpStatus.OK : HttpStatus.CONFLICT));
    }

    @GetMapping("/votes")
    public List<Vote> getVotesForUser() throws Exception {
        int userId = SecurityUtil.authUserId();
        return voteService.history(userId);
    }

    @GetMapping("/todayVotes")
    public List<VoteStatistic> getTodayVotes() {
        return voteService.getTodayVotes(LocalDate.now());
    }
}
