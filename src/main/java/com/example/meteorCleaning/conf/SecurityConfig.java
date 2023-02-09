package com.example.meteorCleaning.conf;

import com.example.meteorCleaning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class SecurityConfig {

    @Autowired
    private UserService userService;


    @Bean
    protected InMemoryUserDetailsManager configureAuthentication() {

        List<UserDetails> userDetails = new ArrayList<>();
        List<GrantedAuthority> userRoles = new ArrayList<>();
        userRoles.add(new SimpleGrantedAuthority("USER"));

        List<GrantedAuthority> adminRoles = new ArrayList<>();
        adminRoles.add(new SimpleGrantedAuthority("ADMIN"));

        userDetails.add(new User("user",
                "$2a$10$VjN621DDdOtCNQeCeptaF.oz.17OajR10LDHR4qAZwFLANS0uym/C",
                userRoles));
        userDetails.add(new User("admin",
                "$2a$10$WQ0Z2uk0Lz.CU0LKMeXS9.xjaN/DL5ALLrlbYmorhNCLE2FcNQDT2",
                adminRoles));
        return new InMemoryUserDetailsManager(userDetails);

    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

//    @Bean
//    protected WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/", "/estimate");
//    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //Exmaple - 1
        http.csrf().disable();
        http.requiresChannel()
                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                .requiresSecure();
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/estimate").permitAll()
                .antMatchers("/admin").hasAuthority("ADMIN")
                .antMatchers("/admin/**").hasAuthority("ADMIN")

                .and().formLogin();
        return http.build();

        //Example - 2

		/*http.authorizeRequests()
		.antMatchers("/employee/fetchall","/employee/fetch/*").authenticated()
		.and().formLogin();*/

        //Example - 3

		/*http.authorizeRequests()
		.antMatchers(getSecureServicesList()).authenticated()
		.and().formLogin();*/

    }
}
