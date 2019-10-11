package com.codegym.config;

import com.codegym.model.User;
import com.codegym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
   private CustomSuccessHandler customSuccessHandler;

    @Autowired
    private UserService userService;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        for (User user : userService.checkAll()) {
            auth.inMemoryAuthentication().withUser(user.getName()).password(user.getPass()).roles(user.getRole());
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/blog", "/blog/view/**","/user/view/**","/registration","/create/user/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/personalPage/**","/edit/blog/**","/delete/blog/**","/create/**").access("hasRole('USER')")
                .and()
                .authorizeRequests().antMatchers("/**").access("hasRole('ADMIN')")
                .and().formLogin()
                .loginPage("/login").permitAll()
                .usernameParameter("name").passwordParameter("pass").successHandler(customSuccessHandler)
                .and().csrf()
                .and().exceptionHandling().accessDeniedPage("/404")
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/blog");
    }
}