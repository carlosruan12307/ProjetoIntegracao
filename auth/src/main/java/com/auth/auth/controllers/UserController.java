package com.auth.auth.controllers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.auth.auth.DTOs.EmailModel;
import com.auth.auth.DTOs.JwtClaimsModel;

import com.auth.auth.responses.UserResponse;
import com.auth.auth.services.JWTService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UserController {
    @Autowired
    UserResponse ur;

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    JWTService jwtService;

    @GetMapping("/")
    public ResponseEntity<String> inicio() {
        return ResponseEntity.ok("bem vindo a api");
    }

    @GetMapping("/getValuesJWT")
    public ResponseEntity<JwtClaimsModel> valuesJWT(HttpServletRequest httpServletRequest) {
        Cookie cookie = WebUtils.getCookie(httpServletRequest, "jwt");
        String jwt = cookie.getValue().toString();
        JwtClaimsModel userModel = jwtService.jwtGetValues(jwt);
        return ResponseEntity.ok().body(userModel);

    }

    @GetMapping("/loginGoogle")
    public ResponseEntity<JwtClaimsModel> loginGoogle(HttpServletRequest httpServletRequest) {
        ur.setMensagem("logado com google");

        String jwt = (String) httpServletRequest.getAttribute("jwtR");
        JwtClaimsModel userModel = jwtService.jwtGetValues(jwt);

        return new ResponseEntity<JwtClaimsModel>(userModel, HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<JwtClaimsModel> login(@AuthenticationPrincipal User user,
            HttpServletRequest httpServletRequest) {
        ur.setMensagem(user.getUsername());

        String jwt = httpServletRequest.getAttribute("jwtR").toString();
        JwtClaimsModel userModel = jwtService.jwtGetValues(jwt);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        EmailModel email = new EmailModel("projetointegracao45607@gmail.com", auth.getName(), "projeto",
                "Parabens! voce fez o login");
        String routingKey = "orders.v1.user-logged";
        rabbitTemplate.convertAndSend(routingKey, email);
        return new ResponseEntity<JwtClaimsModel>(userModel, HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<UserResponse> admin() {
        ur.setMensagem("bem vindo admini");
        return new ResponseEntity<UserResponse>(ur, HttpStatus.OK);
    }

    @GetMapping("/user")
    public String user() {
        return "bem vindo user";
    }

}
