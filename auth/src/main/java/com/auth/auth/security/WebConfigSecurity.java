package com.auth.auth.security;

import java.util.Collections;

import org.apache.catalina.authenticator.BasicAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.auth.auth.filters.GeneratorJWTFilter;
import com.auth.auth.filters.LogoutFilter;
import com.auth.auth.filters.ValidatorJWTFilter;
import com.auth.auth.services.GoogleIdTokenVerifier;
import com.auth.auth.services.JWTService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Configuration
public class WebConfigSecurity {
    @Autowired
    JWTService jwtService;
    @Autowired
    GoogleIdTokenVerifier googleIdTokenVerifier;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .anonymous().disable()
                .cors().configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest arg0) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("*"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        return config;
                    }

                })
                .and()
                .httpBasic()
                .and()
                .addFilterAfter(new GeneratorJWTFilter(jwtService, googleIdTokenVerifier),
                        BasicAuthenticationFilter.class)
                .addFilterBefore(new ValidatorJWTFilter(jwtService), BasicAuthenticationFilter.class)

                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/login").authenticated()
                .requestMatchers(HttpMethod.GET, "/loginGoogle").permitAll()
                .requestMatchers(HttpMethod.GET, "/getValuesJWT").permitAll()
                .requestMatchers(HttpMethod.GET, "/admin").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/").permitAll()

                .and()
                .logout()
                .addLogoutHandler(new LogoutFilter())
                .logoutUrl("/logout")
                .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()))
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
