//package com.codegym.fomater;
//
//import com.codegym.model.User;
//import com.codegym.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.Formatter;
//import org.springframework.stereotype.Component;
//
//import java.text.ParseException;
//import java.util.Locale;
//
//@Component
//public class UserFomater implements Formatter<User> {
//    private UserService userService;
//
//    @Autowired
//    public UserFomater(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Override
//    public User parse(String text, Locale locale) throws ParseException {
//        return userService.findById(Long.parseLong(text));
//    }
//
//    @Override
//    public String print(User object, Locale locale) {
//        return null;
//    }
//}