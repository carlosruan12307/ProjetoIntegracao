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

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Configuration
public class WebConfigSecurity {
    
    
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
                    .csrf().disable()
                    .cors().configurationSource(new CorsConfigurationSource() {

                        @Override
                        public CorsConfiguration getCorsConfiguration(HttpServletRequest arg0) {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(Collections.singletonList("*"));
                            config.setAllowedMethods(Collections.singletonList("*"));
                            config.setAllowedHeaders(Collections.singletonList("Authorization"));
                            return config;
                            }
                        
                    })
                    .and()
                    .httpBasic()
                    .and()
                    .addFilterAfter(new GeneratorJWTFilter(),  BasicAuthenticationFilter.class)
                    .addFilterBefore(new ValidatorJWTFilter(), BasicAuthenticationFilter.class)
                    
                    
                    
                    .authorizeHttpRequests()
                    .requestMatchers(HttpMethod.GET,"/login").authenticated()
                    .requestMatchers(HttpMethod.GET,"/admin").hasAnyRole("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                    .logout()
                    .addLogoutHandler(new LogoutFilter())
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()))
                    .and()
                    .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
