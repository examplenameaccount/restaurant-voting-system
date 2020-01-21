package com.javawebinar.restaurant.service;

import com.javawebinar.restaurant.model.Vote;
import com.javawebinar.restaurant.repository.CrudRestaurantRepository;
import com.javawebinar.restaurant.repository.CrudUserRepository;
import com.javawebinar.restaurant.repository.CrudVoteRepository;
import com.javawebinar.restaurant.to.VoteStatistic;
import com.javawebinar.restaurant.to.VoteTo;
import com.javawebinar.restaurant.util.exception.NotFoundException;
import com.javawebinar.restaurant.web.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class VoteService {
    @Autowired
    CrudVoteRepository crudVoteRepository;

    @Autowired
    CrudRestaurantRepository crudRestaurantRepository;

    @Autowired
    CrudUserRepository crudUserRepository;

    private LocalDate TODAY_DATE = LocalDate.now();

    private boolean notExistByRestaurantId(int restaurantId) {
        return !crudRestaurantRepository.existsById(restaurantId);
    }

    @Transactional
    public VoteTo saveOrUpdate(int userId, int restaurantId) {
        if (notExistByRestaurantId(restaurantId)) {
            throw new NotFoundException("Restaurant with id " + restaurantId + " not found");
        }

        VoteTo voteTo = crudVoteRepository.findByUserIdAndLocalDate(SecurityUtil.authUserId(), TODAY_DATE)
                .map(v -> {
                    v.setRestaurant(crudRestaurantRepository.findById(restaurantId).get());
                    return new VoteTo(v, false);
                })
                .orElseGet(() -> new VoteTo(new Vote(crudUserRepository.getOne(userId),
                        crudRestaurantRepository.findById(restaurantId).get(),
                        TODAY_DATE), true));
        crudVoteRepository.save(voteTo.getVote());
        return voteTo;
    }

    @Transactional
    public VoteTo save(int userId, int restaurantId) {
        if (notExistByRestaurantId(restaurantId)) {
            throw new NotFoundException("Restaurant with id " + restaurantId + " not found");
        }
        Vote todayVote = crudVoteRepository.findByUserIdAndLocalDate(userId, TODAY_DATE).orElse(null);
        if (todayVote != null) {
            return new VoteTo(todayVote, false);
        }
        return new VoteTo(crudVoteRepository.save(new Vote(crudUserRepository.getOne(userId),
                crudRestaurantRepository.findById(restaurantId).get(),
                TODAY_DATE)), true);

    }

    public List<Vote> history(int userId) {
        return crudVoteRepository.getByUserId(userId);
    }

    public List<VoteStatistic> getTodayVotes(LocalDate now) {
        return crudVoteRepository.getTodayVoteStatistic(now);
    }
}

