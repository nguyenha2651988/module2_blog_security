package com.codegym.controller;

import com.codegym.model.Blog;
import com.codegym.model.User;
import com.codegym.service.BlogService;
import com.codegym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    private String getPrincipal() {
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    @ModelAttribute("user")
    public String user() {
        return getPrincipal();
    }

    @ModelAttribute("userName")
    public User fillAllUser(Pageable pageable){
        Long id = blogService.checkIdUser(user(),pageable);
        return userService.findById(id);
    }


    @GetMapping("/blog")
    public ModelAndView findAllBlog(Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("/blog/list");
        Page<Blog> blogs = blogService.findAll(pageable);
        modelAndView.addObject("blogs", blogs);
        return modelAndView;
    }

    @GetMapping("/blog/view/{id}")
    public ModelAndView viewBlog(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/blog/viewBlog");
        Blog blog = blogService.findById(id);
        modelAndView.addObject("blog", blog);
        return modelAndView;
    }

    @GetMapping("/user/view/{id}")
    public ModelAndView viewUser(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/blog/viewUser");
        User user = userService.findById(id);
        modelAndView.addObject("users", user);
        return modelAndView;
    }

    @GetMapping("/admin")
    public ModelAndView backHomeAdmin(Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("/user/admin");
        Page<Blog> blogs = blogService.findAll(pageable);
        Page<User> users = userService.findAll(pageable);
        modelAndView.addObject("blogs", blogs);
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping("/personalPage")
    public ModelAndView toPersonalPage(Pageable pageable) {
        Long id = blogService.checkIdUser(user(), pageable);
        ModelAndView modelAndView = new ModelAndView("/blog/personalPage");
        modelAndView.addObject("personalBlog", blogService.findAllByUser_Id(id, pageable));
        return modelAndView;
    }

    @GetMapping("/edit/blog/{id}")
    public ModelAndView toEditBlog(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/blog/edit");
        modelAndView.addObject("editBlog", blogService.findById(id));
        return modelAndView;
    }



    @GetMapping("/delete/blog/{id}")
    public ModelAndView toDeleteBlog(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/blog/delete");
        modelAndView.addObject("blog", blogService.findById(id));
        return modelAndView;
    }

    @PostMapping("/delete/blog/{id}")
    public ModelAndView DeleteBlog(@PathVariable("id") Long id, RedirectAttributes redirec) {
        ModelAndView modelAndView = new ModelAndView("redirect:/personalPage");
        redirec.addFlashAttribute("message", "Delete a new blog successfully");
        blogService.delete(id);
        return modelAndView;
    }

    @GetMapping("/create/blog/")
    public ModelAndView toCreateBlog() {
        ModelAndView modelAndView = new ModelAndView("/blog/create");
        modelAndView.addObject("createBlog", new Blog());
        return modelAndView;
    }
    @PostMapping("/create/blog/")
    public ModelAndView createBlog(@ModelAttribute("createBlog") Blog blog, RedirectAttributes redirec) {
        ModelAndView modelAndView = new ModelAndView("redirect:/personalPage");
        redirec.addFlashAttribute("message", "Create a new blog successfully");
        blog.setUser(blog.getUser());
        blogService.save(blog);
        return modelAndView;
    }
    @PostMapping("/edit/blog/")
    public ModelAndView editBlog(@ModelAttribute("editBlog") Blog blog, RedirectAttributes redirec) {
        ModelAndView modelAndView = new ModelAndView("redirect:/personalPage");
        redirec.addFlashAttribute("message", "Update a new blog successfully");
        blogService.save(blog);
        return modelAndView;
    }
}
