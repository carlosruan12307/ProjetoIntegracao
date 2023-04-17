package com.notify.notify.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class EmailModel {
    private String emailFrom;
    private String emailTo;
    private String subject;
    private String text;

}
