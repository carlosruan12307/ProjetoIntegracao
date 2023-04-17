package com.auth.auth.DTOs;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserGoogleModel {
    private String id;
    private String email;
    private Boolean emailVerified;
    private String name;
    private String pictureUrl;
}
