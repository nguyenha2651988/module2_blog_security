package com.codegym.service;

import com.codegym.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    Page<Blog> findAll(Pageable pageable);

    Blog findById(Long id);

    void save(Blog blog);

    void delete(Long id);

    Page<Blog> findAllByUser_Id(Long id, Pageable pageable);

    Long checkIdUser(String name, Pageable pageable);

}
