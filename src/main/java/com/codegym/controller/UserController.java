package com.codegym.controller;

import com.codegym.model.User;
import com.codegym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {


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

    @ModelAttribute("users")
    public String user() {
        return getPrincipal();
    }

    @GetMapping("/login")
    public ModelAndView login() {
        if (getPrincipal().equals("anonymousUser")) {
            return new ModelAndView("/user/login");
        }
        return new ModelAndView("redirect:/blog");
    }


    @GetMapping("/accessDenied")
    public ModelAndView backAdmin404() {
        return new ModelAndView("/user/404");
    }

    @GetMapping("/registration")
    public ModelAndView createUserForm() {
        ModelAndView modelAndView = new ModelAndView("/user/create");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/create/user")
    public ModelAndView createUser(@ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView("redirect:/blog");
        userService.save(user);
        return modelAndView;
    }
}
