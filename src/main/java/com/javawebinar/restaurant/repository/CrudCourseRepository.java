package com.javawebinar.restaurant.repository;

import com.javawebinar.restaurant.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional(readOnly = true)
public interface CrudCourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findAllByMenuId(Integer menuId);
}
