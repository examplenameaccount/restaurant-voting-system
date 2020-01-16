package com.javawebinar.restaurant.web.course;

import com.javawebinar.restaurant.CourseTestData;
import com.javawebinar.restaurant.RestaurantTestData;
import com.javawebinar.restaurant.model.Course;
import com.javawebinar.restaurant.model.Role;
import com.javawebinar.restaurant.model.User;
import com.javawebinar.restaurant.service.CourseService;
import com.javawebinar.restaurant.util.exception.NotFoundException;
import com.javawebinar.restaurant.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.javawebinar.restaurant.CourseTestData.*;
import static com.javawebinar.restaurant.MenuTestData.*;
import static com.javawebinar.restaurant.TestUtil.readFromJson;
import static com.javawebinar.restaurant.UserTestData.*;
import static com.javawebinar.restaurant.util.exception.ErrorType.VALIDATION_ERROR;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CourseControllerTest extends AbstractControllerTest {
    @Autowired
    CourseService courseService;

    public CourseControllerTest() {
        super(CourseController.REST_URL);
    }

    @Test
    void create() throws Exception {
        Course newCourse = CourseTestData.getNew();
        ResultActions action = perform(doPost("menus/" + MENU1_ID + "/course").jsonBody(newCourse).basicAuth(ADMIN));

        Course created = readFromJson(action, Course.class);
        Integer newId = created.getId();
        newCourse.setId(newId);
        COURSE_MATCHERS.assertMatch(created, newCourse);
        COURSE_MATCHERS.assertMatch(courseService.get(newId), newCourse);
    }

    @Test
    void createForbidden() throws Exception {
        Course newCourse = CourseTestData.getNew();
        perform(doPost("menus/" + MENU1_ID + "/course").jsonBody(newCourse).basicAuth(USER3))
                .andExpect(status().isForbidden());
    }

    @Test
    void getCoursesForMenu() throws Exception {
        perform(doGet("/menus/" + MENU1_ID + "/courses").basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(COURSE_MATCHERS.contentJson(CourseTestData.courseListForMenu1));
    }

    @Test
    void getAllCourses() throws Exception {
        perform(doGet("/courses").basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(COURSE_MATCHERS.contentJson(CourseTestData.allCourses));
    }

    @Test
    void get() throws Exception {
        perform(doGet("/courses/" + COURSE1_ID).basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(COURSE_MATCHERS.contentJson(CourseTestData.COURSE1));
    }

    @Test
    void delete() throws Exception {
        perform(doDelete("/courses/" + COURSE1_ID).basicAuth(ADMIN))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> courseService.get(COURSE1_ID));
    }

    @Test
    void deleteForbidden() throws Exception {
        perform(doDelete("/courses/" + COURSE1_ID).basicAuth(USER3))
                .andExpect(status().isForbidden());
    }

    @Test
    void update() throws Exception {
        Course updateCourse = CourseTestData.getUpdated();

        perform(doPut("courses/" + COURSE1_ID)
                .jsonBody(updateCourse).basicAuth(ADMIN))
                .andExpect(status().isNoContent());

        COURSE_MATCHERS.contentJson(courseService.get(COURSE1_ID), updateCourse);
    }

    @Test
    void updateForbidden() throws Exception {
        Course updateCourse = CourseTestData.getUpdated();

        perform(doPut("courses/" + COURSE1_ID)
                .jsonBody(updateCourse).basicAuth(USER3))
                .andExpect(status().isForbidden());
    }


    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        Course expected = new Course(null, "Pizza Pepperoni", 150, MENU1);
        perform(doPost("menus/" + MENU1_ID + "/course").jsonBody(expected).basicAuth(ADMIN))
                .andExpect(status().isConflict());

    }
}