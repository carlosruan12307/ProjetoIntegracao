package com.auth.auth.controllers;

import java.security.Security;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.auth.models.EmailModel;
import com.auth.auth.responses.UserResponse;

@RestController
public class UserController {
    @Autowired
    UserResponse ur;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/")
    public String inicio(){
        return "bem vindo a api";
    }

    @GetMapping("/login")
    public ResponseEntity<UserResponse> inicio(@AuthenticationPrincipal User user) {
        ur.setMensagem(user.getUsername());
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        EmailModel email = new EmailModel("projetointegracao789@gmail.com",auth.getName(),"projeto","Parabens! voce fez o login");
        String routingKey = "orders.v1.user-logged";
        rabbitTemplate.convertAndSend(routingKey, email);
        return new ResponseEntity<UserResponse>(ur, HttpStatus.OK);
    }

    @GetMapping("/admin")
    public String admin() {
        return "bem vindo admin";
    }

    @GetMapping("/user")
    public String user() {
        return "bem vindo user";
    }

}
