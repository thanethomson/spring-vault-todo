package com.thanethomson.demos.todo.config;

import com.thanethomson.demos.todo.repos.UserRepo;
import com.thanethomson.demos.todo.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${todo.admin-user.enabled}")
    Boolean adminUserEnabled;

    @Value("${todo.admin-user.password}")
    String adminPassword;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(16);
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        return new CustomUserDetailsService(
                userRepo,
                adminUserEnabled,
                adminPassword,
                passwordEncoder
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/**").csrf().disable();
        http
                .authorizeRequests()
                        .antMatchers("/api/**").authenticated()
                .and()
                        .httpBasic();
    }

}
