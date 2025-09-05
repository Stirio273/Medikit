package com.madaproject.Medikit.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // pour tester facilement
            .authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll() // register et login sans auth
                .anyRequest().authenticated() // tout le reste nécessite auth
            .and()
            .formLogin().disable(); // désactive la page de login par défaut

        return http.build();
    }
}
