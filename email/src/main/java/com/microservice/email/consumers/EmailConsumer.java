package com.microservice.email.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.microservice.email.dtos.EmailRecordDto;

@Component
public class EmailConsumer {
    
    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload EmailRecordDto emailRecordDto){
        System.out.println(emailRecordDto.emailTo());
        System.out.println(emailRecordDto.text());
    }
    
}
