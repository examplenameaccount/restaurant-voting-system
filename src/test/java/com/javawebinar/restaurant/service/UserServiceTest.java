package com.javawebinar.restaurant.service;

import com.javawebinar.restaurant.model.Role;
import com.javawebinar.restaurant.model.User;
import com.javawebinar.restaurant.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.javawebinar.restaurant.UserTestData.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService service;

    @Test
    void create() throws Exception {
        User newUser = getNew();
        User created = service.create(new User(newUser));
        Integer newId = created.getId();
        newUser.setId(newId);
        USER_MATCHERS.assertMatch(created, newUser);
        USER_MATCHERS.assertMatch(service.get(newId), newUser);
    }

    @Test
    void duplicateMailCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "Duplicate", "andrey@yandex.ru", "newPass", Role.ROLE_USER)));
    }

    @Test
    void delete() throws Exception {
        service.delete(USER1_ID);
        assertThrows(NotFoundException.class, () ->
                service.delete(USER1_ID));
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.delete(1));
    }

    @Test
    void get() throws Exception {
        User user = service.get(ADMIN_ID);
        USER_MATCHERS.assertMatch(user, ADMIN);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.get(1));
    }

    @Test
    void getByEmail() throws Exception {
        User user = service.getByEmail("admin@gmail.com");
        USER_MATCHERS.assertMatch(user, ADMIN);
    }

    @Test
    void update() throws Exception {
        User updated = getUpdated();
        service.update(new User(updated));
        USER_MATCHERS.assertMatch(service.get(USER1_ID), updated);
    }

    @Test
    void getAll() throws Exception {
        List<User> all = service.getAll();
        USER_MATCHERS.assertMatch(all, userList);
    }

    @Test
    void createWithException() throws Exception {
        validateRootCause(() -> service.create(new User(null, "  ", "mail@yandex.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "  ", Role.ROLE_USER)), ConstraintViolationException.class);
    }

    @Test
    void enable() {
        service.enable(USER1_ID, false);
        assertFalse(service.get(USER1_ID).isEnabled());
        service.enable(USER1_ID, true);
        assertTrue(service.get(USER1_ID).isEnabled());
    }
}