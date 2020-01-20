package com.javawebinar.restaurant.repository;

import com.javawebinar.restaurant.model.Vote;
import com.javawebinar.restaurant.to.VoteStatistic;
import com.javawebinar.restaurant.to.VoteTo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    Optional<Vote> findByUserIdAndLocalDate(int authUserId, LocalDate now);

    List<Vote> getByUserId(@Param("userId") int userId);

    @Query("SELECT " +
            "    new com.javawebinar.restaurant.to.VoteStatistic(v.restaurant, COUNT(v)) " +
            "FROM " +
            "    Vote v " +
            "where v.localDate=:today GROUP BY " +
            "    v.restaurant")
    List<VoteStatistic> getTodayVoteStatistic(@Param("today") LocalDate today);

}
