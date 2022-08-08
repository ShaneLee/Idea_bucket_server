package ee.shanel.ideabucket.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class EmailSenderServiceTest
{
    private static final MailMessage MESSAGE = new SimpleMailMessage();

    @Mock
    private JavaMailSender mockMailSender;

    private EmailSenderService subject;

    @BeforeEach
    void setUp()
    {
        subject = new EmailSenderService(
                mockMailSender
        );
    }

    @Test
    void itSendsEmail()
    {
        StepVerifier.create(subject.send(MESSAGE))
                .verifyComplete();

        Mockito.verify(mockMailSender).send(Mockito.any(SimpleMailMessage.class));
    }
}
