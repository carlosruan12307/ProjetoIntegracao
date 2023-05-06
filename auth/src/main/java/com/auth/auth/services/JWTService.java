package com.auth.auth.services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import org.springframework.stereotype.Service;

import com.auth.auth.DTOs.JwtClaimsModel;

import com.auth.auth.models.UserModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String jwtGenerator(UserModel userModel) {

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        return Jwts.builder()
                .claim("email", userModel.getEmail())
                .claim("authorities", populateAuthorities(userModel.getAuthorities()))
                .claim("pictureURL", userModel.getPictureURL())
                .setSubject("jwt")
                .setIssuer("projeto")
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 3000000))
                .signWith(key)
                .compact();

    }

    public Authentication jwtValidator(String jwt) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        String email = String.valueOf(claims.get("email"));
        String authorities = String.valueOf(claims.get("authorities"));
        Authentication auth = new UsernamePasswordAuthenticationToken(email, null,
                AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));

        return auth;
    }

    public JwtClaimsModel jwtGetValues(String jwt) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        String email = String.valueOf(claims.get("email"));
        String authorities = String.valueOf(claims.get("authorities"));
        String pictureURL = String.valueOf(claims.get("pictureURL"));
        JwtClaimsModel userModel = new JwtClaimsModel(email, authorities, pictureURL);
        return userModel;

    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());

        }
        return String.join(",", authoritiesSet);

    }

}
