package com.auth.auth.responses;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class GoogleResponse {
    private String client_id;
    private String credential;
}
