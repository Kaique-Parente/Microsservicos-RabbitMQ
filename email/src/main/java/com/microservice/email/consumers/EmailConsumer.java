package com.microservice.email.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.microservice.email.dtos.EmailRecordDto;
import com.microservice.email.models.EmailModel;
import com.microservice.email.services.EmailService;

@Component
public class EmailConsumer {

    final EmailService emailService;

    public EmailConsumer(EmailService emailService){
        this.emailService = emailService;
    }
    
    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload EmailRecordDto emailRecordDto){
        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(emailRecordDto, emailModel);
        
        emailService.sendEmail(emailModel);
    }
    
}
