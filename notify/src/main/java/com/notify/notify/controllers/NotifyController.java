package com.notify.notify.controllers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.notify.notify.models.EmailModel;
import com.notify.notify.services.SendEmailService;

@Component
public class NotifyController {
    @Autowired
    private SendEmailService emailService;

    @RabbitListener(queues = "orders.v1.user-logged")
    public void notify(EmailModel email) {
        System.out.println(email);
        emailService.enviar(email);

    }
}
