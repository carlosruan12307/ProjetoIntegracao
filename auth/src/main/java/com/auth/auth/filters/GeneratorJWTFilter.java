package com.auth.auth.filters;

import java.io.IOException;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.filter.OncePerRequestFilter;

import com.auth.auth.models.UserModel;
import com.auth.auth.services.GoogleIdTokenVerifier;
import com.auth.auth.services.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GeneratorJWTFilter extends OncePerRequestFilter {
    // @Value("{jwt.secretKey}")
    // private String secretKey;

    GoogleIdTokenVerifier googleIdTokenVerifier;

    JWTService jwtService;

    public GeneratorJWTFilter(JWTService jwtService, GoogleIdTokenVerifier googleIdTokenVerifier) {
        this.googleIdTokenVerifier = googleIdTokenVerifier;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && !(auth instanceof AnonymousAuthenticationToken)
                    && (request.getHeader("credentials") == null)) {
                UserModel userModel = new UserModel(auth.getName(), auth.getAuthorities(),
                        "http://localhost:8080/images/teste.png");
                String jwt = jwtService.jwtGenerator(userModel);
                Cookie cookie = new Cookie("jwt", jwt);
                cookie.setHttpOnly(true);
                request.setAttribute("jwtR", jwt);
                response.addCookie(cookie);

            } else if (request.getHeader("credentials") != null && request.getServletPath().equals("/loginGoogle")) {
                UserModel jwtClaims = googleIdTokenVerifier
                        .verifierAndReturnDataUser(request.getHeader("credentials"));
                String jwt = jwtService.jwtGenerator(jwtClaims);
                Cookie cookie = new Cookie("jwt", jwt);
                cookie.setHttpOnly(true);
                request.setAttribute("jwtR", jwt);
                response.addCookie(cookie);

            }
            filterChain.doFilter(request, response);
        }

        catch (Exception e) {
            throw new BadCredentialsException("Invalid Request");
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // TODO Auto-generated method stub
        return !request.getServletPath().equals("/loginGoogle") && !request.getServletPath().equals("/login");
    }

}
