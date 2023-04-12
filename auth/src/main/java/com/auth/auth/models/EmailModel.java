package com.auth.auth.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class EmailModel implements Serializable {
    private String emailFrom;
    private String emailTo;
    private String subject;
    private String text;
        

}

