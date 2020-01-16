package com.javawebinar.restaurant;

import com.javawebinar.restaurant.model.Role;
import com.javawebinar.restaurant.model.User;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.javawebinar.restaurant.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final int USER1_ID = START_SEQ;
    public static final int USER2_ID = START_SEQ + 1;
    public static final int USER3_ID = START_SEQ + 2;
    public static final int USER4_ID = START_SEQ + 3;
    public static final int ADMIN_ID = START_SEQ + 4;

    public static final User USER1 = new User(USER1_ID, "Andrey", "andrey@yandex.ru", "123456", Role.ROLE_USER);
    public static final User USER2 = new User(USER2_ID, "Vladimir", "vladimr@yandex.ru", "qwerty", Role.ROLE_USER);
    public static final User USER3 = new User(USER3_ID, "Vasiliy", "vasiliy@yandex.ru", "ytrewq", Role.ROLE_USER);
    public static final User USER4 = new User(USER4_ID, "Dmitriy", "dmitriy@yandex.ru", "ytrewq1234", Role.ROLE_USER);

    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static List<User> userList = List.of(ADMIN, USER1, USER4, USER3, USER2);
    public static List<User> userListWithoutUser1 = List.of(ADMIN, USER4, USER3, USER2);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.ROLE_USER));
    }

    public static User getUpdated() {
        User updated = new User(USER1);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        return updated;
    }

    public static TestMatchers<User> USER_MATCHERS = TestMatchers.useFieldsComparator(User.class, "registered", "password", "votes");
}
