package com.auth.auth.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth.auth.DTOs.EmailModel;
import com.auth.auth.DTOs.UserGoogleModel;
import com.auth.auth.responses.GoogleResponse;
import com.auth.auth.responses.UserResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.http.HttpTransport;

@RestController
public class UserController {
    @Autowired
    UserResponse ur;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    com.auth.auth.services.GoogleIdTokenVerifier google;

    @GetMapping("/")
    public ResponseEntity<String> inicio() {
        return ResponseEntity.ok("bem vindo a api");
    }

    @PostMapping("/loginGoogle")

    public ResponseEntity<UserGoogleModel> loginGoogle(@RequestBody GoogleResponse response)
            throws GeneralSecurityException, IOException {

        return ResponseEntity.ok().body(google.verifierAndReturnDataUser(response));

    }

    @GetMapping("/login")
    public ResponseEntity<UserResponse> login(@AuthenticationPrincipal User user) {
        ur.setMensagem(user.getUsername());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        EmailModel email = new EmailModel("projetointegracao45607@gmail.com", auth.getName(), "projeto",
                "Parabens! voce fez o login");
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
