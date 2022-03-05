package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.config.EmailProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class EmailSenderServiceTest
{
    private static final String EMAIL = "TEST";

    private static final String USER = "USER";

    private static final String TOKEN = "TOKEN";

    private static final EmailProperties PROPERTIES = createEmailProperties();

    @Mock
    private JavaMailSender mockMailSender;

    private EmailSenderService subject;

    @BeforeEach
    void setUp()
    {
        subject = new EmailSenderService(
                mockMailSender,
                PROPERTIES
        );
    }

    @Test
    void itSendsEmail()
    {
        StepVerifier.create(subject.send(USER, TOKEN))
                .verifyComplete();

        Mockito.verify(mockMailSender).send(Mockito.any(SimpleMailMessage.class));
    }

    private static EmailProperties createEmailProperties()
    {
        return EmailProperties.builder()
                .sender(EMAIL)
                .build();
    }
}
