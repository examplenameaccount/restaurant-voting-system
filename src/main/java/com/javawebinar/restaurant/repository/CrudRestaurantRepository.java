package com.javawebinar.restaurant.repository;

import com.javawebinar.restaurant.model.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {
//    @EntityGraph(attributePaths = {"courses", "menus"}, type = EntityGraph.EntityGraphType.FETCH)
////    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menus m JOIN FETCH m.courses c where m.dateTime = ?1 and m.restaurant = r.id and c.menu = m.id")
//    List<Restaurant> findAllByName(String name);
}
