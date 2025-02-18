package org.greatreads.security.config;

import lombok.AllArgsConstructor;
import org.greatreads.security.jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Public
                        .requestMatchers("/books/**").permitAll()
                        .requestMatchers("/user/login").permitAll()
                        .requestMatchers("/reviews/books/{bookId}").permitAll()
                        //Admin
                        .requestMatchers("/administrator/**").hasAuthority(("ROLE_ADMIN"))
                        //Author

                        //Reader
                        .requestMatchers("/reader/**").hasAnyAuthority("ROLE_READER", "ROLE_ADMIN")

                        .requestMatchers(HttpMethod.POST, "/reviews").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/reviews/{reviewId}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/reviews/{reviewId}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/reviews/users/{userId}").authenticated()
                        .anyRequest().authenticated())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}