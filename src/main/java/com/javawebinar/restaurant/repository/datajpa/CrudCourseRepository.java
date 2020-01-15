package com.javawebinar.restaurant.repository.datajpa;

import com.javawebinar.restaurant.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional(readOnly = true)
public interface CrudCourseRepository extends JpaRepository<Course, Integer> {
    Optional<List<Course>> findAllByMenuId(Integer menuId);
}
