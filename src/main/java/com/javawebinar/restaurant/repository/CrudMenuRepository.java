package com.javawebinar.restaurant.repository;

import com.javawebinar.restaurant.model.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMenuRepository extends JpaRepository<Menu, Integer> {
    Optional<Menu> findById(Integer id);

    Optional<List<Menu>> findAllByRestaurantId(Integer restaurantId);

    @EntityGraph(attributePaths = {"restaurant", "courses"})
    List<Menu> findAllByDateTime(LocalDate dateTime);
}
