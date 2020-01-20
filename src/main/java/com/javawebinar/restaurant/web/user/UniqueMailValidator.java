package com.javawebinar.restaurant.web.user;


import com.javawebinar.restaurant.HasEmail;
import com.javawebinar.restaurant.model.User;
import com.javawebinar.restaurant.repository.CrudUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;


@Component
public class UniqueMailValidator implements org.springframework.validation.Validator {

    @Autowired
    private CrudUserRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return HasEmail.class.isAssignableFrom(clazz);
    }

    public static final String EXCEPTION_DUPLICATE_EMAIL = "User with this email exist";

    @Override
    public void validate(Object target, Errors errors) {
        HasEmail user = ((HasEmail) target);
        User dbUser = repository.getByEmail(user.getEmail().toLowerCase()).orElse(null);
        if (dbUser != null && !dbUser.getId().equals(user.getId())) {
            errors.rejectValue("email", EXCEPTION_DUPLICATE_EMAIL);
        }
    }
}
