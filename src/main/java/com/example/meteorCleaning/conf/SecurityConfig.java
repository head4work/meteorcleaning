package com.example.meteorCleaning.conf;

import com.example.meteorCleaning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(getPasswordEncoder());
        return authProvider;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());
        http.csrf().disable();
        http.requiresChannel()
                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                .requiresSecure();
        http.authorizeRequests()
                .antMatchers("/", "/estimate", "/login", "/prices", "/dates", "/css/**", "/images/**", "/js/**", "/perform_login").permitAll()
                .antMatchers(HttpMethod.POST, "/rest/profile").permitAll()
                .antMatchers("/admin", "/admin/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/")
                .usernameParameter("email")
                .passwordParameter("password")
                .loginProcessingUrl("/perform_login").permitAll()
                .successHandler(successHandler())
                //.defaultSuccessUrl("/admin", true)
                .failureHandler(authenticationFailureHandler());
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
//                .antMatchers("/css/**","/images/**","/js/**");
//    }

/*    @Bean
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

    }*/


//    @Bean
//    protected WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/", "/estimate");
//    }


}
