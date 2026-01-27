package com.microservice.email.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailException;

import com.microservice.email.enums.StatusEmail;
import com.microservice.email.models.EmailModel;
import com.microservice.email.services.EmailService;
import com.microservice.email.services.SendEmailService;

@ExtendWith(MockitoExtension.class)
class SendEmailUseCaseTest {

    @Mock
    SendEmailService sendEmailService;

    @Mock
    EmailService emailService;

    @InjectMocks
    SendEmailUseCase sendEmailUseCase;

    @Test
    void shouldSendEmailAndSaveWithStatusSent() {
        // GIVEN
        EmailModel emailModel = EmailModel.builder()
                .emailTo("test@email.com")
                .subject("Teste")
                .text("Olá")
                .build();

        // WHEN
        sendEmailUseCase.execute(emailModel);

        // THEN
        assertEquals(StatusEmail.SENT, emailModel.getStatusEmail());

        verify(sendEmailService).sendEmail(emailModel);
        verify(emailService).save(emailModel);
    }

    @Test
    void shouldSaveEmailWithStatusError() {
        var emailModel = EmailModel.builder().build();

        // Mocka sendEmailService para lançar exceção
        doThrow(new MailException("SMTP ERRO") {})
            .when(sendEmailService)
            .sendEmail(any(EmailModel.class));

        // Mocka emailService.save para retornar o emailModel que foi passado
        when(emailService.save(any(EmailModel.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        var result = sendEmailUseCase.execute(emailModel);

        assertEquals(StatusEmail.ERROR, result.getStatusEmail());

        verify(sendEmailService).sendEmail(emailModel);
        verify(emailService).save(emailModel);
    }
}
