package com.javawebinar.restaurant.web.user;

import com.javawebinar.restaurant.TestUtil;
import com.javawebinar.restaurant.UserTestData;
import com.javawebinar.restaurant.model.Role;
import com.javawebinar.restaurant.model.User;
import com.javawebinar.restaurant.repository.CrudUserRepository;
import com.javawebinar.restaurant.service.UserService;
import com.javawebinar.restaurant.util.exception.NotFoundException;
import com.javawebinar.restaurant.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.javawebinar.restaurant.UserTestData.*;
import static com.javawebinar.restaurant.util.exception.ErrorType.VALIDATION_ERROR;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminRestControllerTest extends AbstractControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private CrudUserRepository dataJpaUserRepository;

    AdminRestControllerTest() {
        super(AdminRestController.REST_URL);
    }

    @Test
    void test() {
        User newUser = UserTestData.getNew();
        dataJpaUserRepository.save(newUser);
    }

    @Test
    void get() throws Exception {
        perform(doGet(ADMIN_ID).basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHERS.contentJson(ADMIN));
    }

    @Test
    void getNotFound() throws Exception {
        perform(doGet(1).basicAuth(ADMIN))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void getByEmail() throws Exception {
        perform(doGet("by?email={email}", ADMIN.getEmail()).basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHERS.contentJson(ADMIN));
    }

    @Test
    void delete() throws Exception {
        perform(doDelete(USER1_ID).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> userService.get(USER1_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(doDelete(1).basicAuth(ADMIN))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(doGet())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getForbidden() throws Exception {
        perform(doGet().basicAuth(USER1))
                .andExpect(status().isForbidden());
    }

    @Test
    void update() throws Exception {
        User updated = UserTestData.getUpdated();
        perform(doPut(USER1_ID).jsonUserWithPassword(updated).basicAuth(ADMIN))
                .andExpect(status().isNoContent());

        USER_MATCHERS.assertMatch(userService.get(USER1_ID), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        User newUser = UserTestData.getNew();
        ResultActions action = perform(doPost().jsonUserWithPassword(newUser).basicAuth(ADMIN))
                .andExpect(status().isCreated());

        User created = TestUtil.readFromJson(action, User.class);
        Integer newId = created.getId();
        newUser.setId(newId);
        USER_MATCHERS.assertMatch(created, newUser);
        USER_MATCHERS.assertMatch(userService.get(newId), newUser);
    }

    @Test
    void getAll() throws Exception {
        perform(doGet().basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHERS.contentJson(userList));
    }

    @Test
    void enable() throws Exception {
        perform(doPatch(USER1_ID).basicAuth(ADMIN).unwrap()
                .param("enabled", "false")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertFalse(userService.get(USER1_ID).isEnabled());
    }


    @Test
    void createInvalid() throws Exception {
        User expected = new User(null, null, "", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        perform(doPost().jsonBody(expected).basicAuth(ADMIN))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void updateInvalid() throws Exception {
        User updated = new User(USER1);
        updated.setName("");
        perform(doPut(USER1_ID).jsonBody(updated).basicAuth(ADMIN))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        User updated = new User(USER1);
        updated.setEmail("admin@gmail.com");
        perform(doPut(USER1_ID).jsonUserWithPassword(updated).basicAuth(ADMIN))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        User expected = new User(null, "New", "andrey@yandex.ru", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        perform(doPost().jsonUserWithPassword(expected).basicAuth(ADMIN))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));

    }
}