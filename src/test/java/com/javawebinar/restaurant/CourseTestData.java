package com.javawebinar.restaurant;

import com.javawebinar.restaurant.model.Course;

import java.util.List;

import static com.javawebinar.restaurant.MenuTestData.*;
import static com.javawebinar.restaurant.model.AbstractBaseEntity.START_SEQ;

public class CourseTestData {
    public static final int COURSE1_ID = START_SEQ + 11;

    public static final Course COURSE1 = new Course(COURSE1_ID, "Pizza Pepperoni", 100, MENU1);
    public static final Course COURSE2 = new Course(COURSE1_ID + 1, "Pizza Margarita", 120, MENU1);
    public static final Course COURSE3 = new Course(COURSE1_ID + 2, "Salad", 40, MENU1);
    public static final Course COURSE4 = new Course(COURSE1_ID + 3, "Сheeseburger", 50, MENU2);
    public static final Course COURSE5 = new Course(COURSE1_ID + 4, "Big Mac", 60, MENU2);
    public static final Course COURSE6 = new Course(COURSE1_ID + 5, "Hot-Dog", 40, MENU3);
    public static final Course COURSE7 = new Course(COURSE1_ID + 6, "Longer Cheese", 50, MENU3);

    public static final TestMatchers<Course> COURSE_MATCHERS = TestMatchers.useFieldsComparator(Course.class, "menu");

    public static Course getNew() {
        return new Course(null, "New", 100);
    }

    public static Course getUpdated() {
        Course updated = new Course(COURSE1);
        updated.setName("UpdatedName");
        updated.setPrice(100);
        updated.setMenu(MENU2);
        return updated;
    }

    public static List<Course> courseListForMenu1 = List.of(COURSE2,COURSE1, COURSE3);

    public static List<Course> allCourses = List.of(COURSE1, COURSE2, COURSE3, COURSE4, COURSE5, COURSE6, COURSE7);
}
