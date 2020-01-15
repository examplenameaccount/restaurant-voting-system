package com.javawebinar.restaurant;

import com.javawebinar.restaurant.web.user.AdminRestController;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

import static com.javawebinar.restaurant.TestUtil.mockAuthorize;
import static com.javawebinar.restaurant.UserTestData.USER1;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/inmemory.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.getAll();
            System.out.println();

            mockAuthorize(USER1);
        }
    }
}
