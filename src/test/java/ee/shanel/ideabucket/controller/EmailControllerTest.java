package ee.shanel.ideabucket.controller;

import ee.shanel.ideabucket.model.MailingList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class EmailControllerTest
{
    private static final String EMAIL = "test@example.com";

    private static final String INVALID_EMAIL = "54";

    private EmailController subject;

    @BeforeEach
    void beforeEach()
    {
        subject = new EmailController();
    }

    @Test
    void itHandlesValidEmail()
    {
        StepVerifier.create(subject.mailingList(MailingList.builder().email(EMAIL).build()))
                .assertNext(res -> Assertions.assertTrue(res.getStatusCode().is2xxSuccessful()))
                .verifyComplete();

    }

    @Test
    void itHandlesAnInvalidEmail()
    {
        StepVerifier.create(subject.mailingList(MailingList.builder().email(INVALID_EMAIL).build()))
                .assertNext(res -> Assertions.assertTrue(res.getStatusCode().is4xxClientError()))
                .verifyComplete();
    }

    @Test
    void itHandlesMissingEmail()
    {
        StepVerifier.create(subject.mailingList(MailingList.builder().build()))
                .assertNext(res -> Assertions.assertTrue(res.getStatusCode().is4xxClientError()))
                .verifyComplete();
    }
}
