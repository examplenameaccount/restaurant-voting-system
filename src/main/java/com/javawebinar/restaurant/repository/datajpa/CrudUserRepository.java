package com.javawebinar.restaurant.repository.datajpa;

import com.javawebinar.restaurant.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudUserRepository extends JpaRepository<User, Integer> {
    Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    Optional<User> getByEmail(String email);

    @Override
    default List<User> findAll() {
        return findAll(SORT_NAME_EMAIL);
    }
}
