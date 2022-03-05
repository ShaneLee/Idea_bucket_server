package ee.shanel.ideabucket.controller;

import ee.shanel.ideabucket.model.Registration;
import ee.shanel.ideabucket.model.User;
import ee.shanel.ideabucket.service.RegistrationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class RegistrationControllerTest
{
    private static final String NAME = "Name";

    private static final String NAME_WITH_SPACE = "Name Last";

    private static final String INVALID_NAME = "<script>";

    private static final String EMAIL = "test@example.com";

    private static final String INVALID_EMAIL = "54";

    private static final User USER = User.builder().id(EMAIL).build();

    @Mock
    private RegistrationService mockRegistrationService;

    private RegistrationController subject;

    @BeforeEach
    void beforeEach()
    {
        subject = new RegistrationController(mockRegistrationService);
    }

    @Test
    void itHandlesValidRegistration()
    {
        final var register = Registration.builder()
                .email(EMAIL)
                .name(NAME)
                .build();

        Mockito.when(mockRegistrationService.register(Mockito.any()))
                .thenReturn(Mono.just(USER));

        StepVerifier.create(subject.register(register))
                .assertNext(res -> Assertions.assertTrue(res.getStatusCode().is2xxSuccessful()))
                .verifyComplete();

        Mockito.verify(mockRegistrationService).register(register);
    }

    @Test
    void itHandlesValidRegistrationWithSpaceInName()
    {
        final var register = Registration.builder()
                .email(EMAIL)
                .name(NAME_WITH_SPACE)
                .build();

        Mockito.when(mockRegistrationService.register(Mockito.any()))
                .thenReturn(Mono.just(USER));

        StepVerifier.create(subject.register(register))
                .assertNext(res -> Assertions.assertTrue(res.getStatusCode().is2xxSuccessful()))
                .verifyComplete();

        Mockito.verify(mockRegistrationService).register(register);
    }

    @Test
    void itHandlesValidRegistrationWithUmlautInName()
    {
        final var register = Registration.builder()
                .email(EMAIL)
                .name("tëst")
                .build();
        final var expected = Registration.builder()
                .email(EMAIL)
                .name("t&euml;st")
                .build();

        Mockito.when(mockRegistrationService.register(Mockito.any()))
                .thenReturn(Mono.just(USER));

        StepVerifier.create(subject.register(register))
                .assertNext(res -> Assertions.assertTrue(res.getStatusCode().is2xxSuccessful()))
                .verifyComplete();

        Mockito.verify(mockRegistrationService).register(expected);
    }

    @Test
    void itHandlesMissingName()
    {
        StepVerifier.create(subject.register(Registration.builder()
                .email(EMAIL)
                .build()))
                .assertNext(res -> Assertions.assertTrue(res.getStatusCode().is4xxClientError()))
                .verifyComplete();
    }

    @Test
    void itHandlesInvalidName()
    {
        Mockito.when(mockRegistrationService.register(Mockito.any()))
                .thenReturn(Mono.just(USER));

        final var registration = Registration.builder()
                .name(INVALID_NAME)
                .email(EMAIL)
                .build();
        final var expected = Registration.builder()
                .name("&lt;script&gt;")
                .email(EMAIL)
                .build();

        StepVerifier.create(subject.register(registration))
                .assertNext(res -> Assertions.assertTrue(res.getStatusCode().is2xxSuccessful()))
                .verifyComplete();

        Mockito.verify(mockRegistrationService).register(expected);
    }

    @Test
    void itHandlesMissingEmail()
    {
        StepVerifier.create(subject.register(Registration.builder()
                .name(NAME)
                .build()))
                .assertNext(res -> Assertions.assertTrue(res.getStatusCode().is4xxClientError()))
                .verifyComplete();
    }

    @Test
    void itHandlesAnInvalidEmail()
    {
        StepVerifier.create(subject.register(Registration.builder()
                .email(INVALID_EMAIL)
                .name(NAME)
                .build()))
                .assertNext(res -> Assertions.assertTrue(res.getStatusCode().is4xxClientError()))
                .verifyComplete();
    }
}
