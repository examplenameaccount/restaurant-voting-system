package com.javawebinar.restaurant.web.course;

import com.javawebinar.restaurant.model.Course;
import com.javawebinar.restaurant.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = CourseController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseController {

    static final String REST_URL = "/rest";

    @Autowired
    CourseService courseService;

    @PostMapping(value = "/menus/{menuId}/course")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Course> create(@Valid @RequestBody Course course,
                                         @PathVariable(value = "menuId") int menuId
    ) {
        Course created = courseService.create(menuId, course);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping(value = "/menus/{menuId}/courses")
    public List<Course> getCoursesForMenu(@PathVariable(value = "menuId") int menuId) {
        return courseService.getCoursesForMenu(menuId);
    }

    @GetMapping(value = "/courses")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping(value = "/courses/{courseId}")
    public Course get(@PathVariable(value = "courseId") int courseId) {
        return courseService.get(courseId);

    }

    @DeleteMapping(value = "/courses/{courseId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "courseId") int courseId) {
        courseService.delete(courseId);

    }

    @PutMapping(value = "/courses/{courseId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Course courseRequest, @PathVariable(value = "courseId") Integer courseId
    ) {
        courseService.update(courseId, courseRequest);
    }
}
