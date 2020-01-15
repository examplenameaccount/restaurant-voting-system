package com.javawebinar.restaurant.service;

import com.javawebinar.restaurant.CourseTestData;
import com.javawebinar.restaurant.model.Course;
import com.javawebinar.restaurant.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.util.List;

import static com.javawebinar.restaurant.CourseTestData.*;
import static com.javawebinar.restaurant.MenuTestData.MENU1;
import static com.javawebinar.restaurant.MenuTestData.MENU1_ID;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CourseServiceTest extends AbstractServiceTest {
    @Autowired
    CourseService courseService;

    @Test
    void create() throws Exception {
        Course newCourse = CourseTestData.getNew();
        Course created = courseService.create(MENU1.getId(), new Course(newCourse));
        Integer newId = created.getId();
        newCourse.setId(newId);
        COURSE_MATCHERS.assertMatch(created, newCourse);
        COURSE_MATCHERS.assertMatch(courseService.get(newId), newCourse);
    }

    @Test
    void duplicateMenuIdNameCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                courseService.create(MENU1_ID, new Course(null, "Pizza Pepperoni", 150)));
    }

    @Test
    void delete() throws Exception {
        courseService.delete(COURSE1_ID);
        assertThrows(NotFoundException.class, () ->
                courseService.delete(COURSE1_ID));
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                courseService.delete(1));
    }

    @Test
    void get() throws Exception {
        Course course = courseService.get(COURSE1_ID);
        COURSE_MATCHERS.assertMatch(course, COURSE1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                courseService.get(1));
    }

    @Test
    void update() throws Exception {
        Course updated = getUpdated();
        courseService.update(COURSE1_ID, new Course(updated));
        COURSE_MATCHERS.assertMatch(courseService.get(COURSE1_ID), updated);
    }

    @Test
    void getAll() throws Exception {
        List<Course> all = courseService.getAllCourses();
        COURSE_MATCHERS.assertMatch(all, allCourses);
    }

    @Test
    void getCoursesForMenu() throws Exception {
        List<Course> all = courseService.getCoursesForMenu(MENU1_ID);
        COURSE_MATCHERS.assertMatch(all, courseListForMenu1);
    }
}
