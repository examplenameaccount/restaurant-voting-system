package com.javawebinar.restaurant.repository.datajpa;

import com.javawebinar.restaurant.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    Optional<Vote> findByUserIdAndLocalDate(int authUserId, LocalDate now);
}
