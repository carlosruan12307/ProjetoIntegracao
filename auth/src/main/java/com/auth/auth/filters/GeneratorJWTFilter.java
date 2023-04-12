package com.auth.auth.filters;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GeneratorJWTFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                try {
                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    if(auth != null){
                        SecretKey key = Keys.hmacShaKeyFor("jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4".getBytes());
                        String jwt = Jwts.builder()
                        .claim("email", auth.getName())
                        .claim("authorities", populateAuthorities(auth.getAuthorities()))
                        .setSubject("jwt")
                        .setIssuer("projeto")
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(new Date().getTime() + 3000000))
                        .signWith(key)
                        .compact();
            
                        Cookie cookie = new Cookie("jwt", jwt);
                        cookie.setHttpOnly(true);
                        response.addCookie(cookie);
            
                    }
                } catch (Exception e) {
                    throw new BadCredentialsException("invalid token");
                }
                
    
        filterChain.doFilter(request, response);
        
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // TODO Auto-generated method stub
        return !request.getServletPath().equals("/login");
    }


          
    private String populateAuthorities(Collection<? extends GrantedAuthority> collection){
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
            
        }
        return String.join(",", authoritiesSet);
    
    }
    
}
