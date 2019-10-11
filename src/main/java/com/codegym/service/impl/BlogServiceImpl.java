package com.codegym.service.impl;

import com.codegym.model.Blog;
import com.codegym.repository.BlogRepository;
import com.codegym.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Page<Blog> findAll(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Blog findById(Long id) {
        return blogRepository.findOne(id);
    }

    @Override
    public void save(Blog blog) {
        blogRepository.save(blog);
    }

    @Override
    public void delete(Long id) {
        blogRepository.delete(id);
    }

    @Override
    public Page<Blog> findAllByUser_Id(Long id, Pageable pageable) {
        return blogRepository.findAllByUser_Id(id, pageable);
    }

    @Override
    public Long checkIdUser(String name, Pageable pageable) {
        Long id = 0l;
        for (Blog blog : blogRepository.findAll(pageable)) {
            if (name.equals(blog.getUser().getName())) {
                id = blog.getUser().getId();
            }
        }
        return id;
    }

}
