package com.example.orgchart.framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ACTUATOR = "/actuator/**";
    private static final String API_V_1 = "/api/v1/**";
    private static final String USERNAME = "username";
    private static final String NOOP_PASSWORD = "{noop}password";
    private static final String USER = "user";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(ACTUATOR).permitAll()
                .antMatchers(API_V_1).authenticated()
                .and()
                .httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(USERNAME)
                .password(NOOP_PASSWORD)
                .roles(USER);
    }
}