package com.pat.SpellsOfEnglish.security;

import com.pat.SpellsOfEnglish.service.Impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class SecurityConfig {
    @Lazy
    private final UserServiceImpl userService;
    private final CustomWebAuthenticationDetailsSource authenticationDetailsSource;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
//                .requestMatchers("/courses", "/auth/registration", "/auth/login", "/css/**").permitAll()
//                .requestMatchers("/users").hasAnyAuthority("ADMINISTRATOR")
                .requestMatchers("/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .userDetailsService(userService)
                .formLogin()
                .loginPage("/login").permitAll()
                .authenticationDetailsSource(authenticationDetailsSource)
                .and()
                .logout().permitAll()
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
