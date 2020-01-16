package com.javawebinar.restaurant.service;

import com.javawebinar.restaurant.model.Course;
import com.javawebinar.restaurant.repository.datajpa.CrudCourseRepository;
import com.javawebinar.restaurant.repository.datajpa.CrudMenuRepository;
import com.javawebinar.restaurant.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CrudCourseRepository courseRepository;

    @Autowired
    private CrudMenuRepository menuRepository;

    @Transactional
    public Course create(int menuId, Course courseRequest) {
        courseRequest.setMenu(menuRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("Menu with id " + menuId + " not found")));
        return courseRepository.save(courseRequest);
    }

    public List<Course> getCoursesForMenu(int menuId) {
        return courseRepository.findAllByMenuId(menuId)
                .orElseThrow(() -> new NotFoundException("Menu with id " + menuId + "not found"));
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course get(int courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course with id " + courseId + "not found"));
    }

    @Transactional
    public void delete(int courseId) {
        courseRepository.delete(courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course with id " + courseId + "not found")));
    }

    @Transactional
    public void update(int courseId, Course courseRequest) {
        Course updateCourse = courseRepository.findById(courseId)
                .map(course -> new Course(course.getId(), courseRequest.getName(), courseRequest.getPrice(), courseRequest.getMenu()))
                .orElseThrow(() -> new NotFoundException("Course with id " + courseId + "not found"));
        courseRepository.save(updateCourse);
    }
}
