package com.codegym.service;

import com.codegym.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<User> findAll(Pageable pageable);

    Iterable<User> checkAll();

    User findById(Long id);

    void save(User user);

    void delete(User user);
}
