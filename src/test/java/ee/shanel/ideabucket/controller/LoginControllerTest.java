package ee.shanel.ideabucket.controller;

import ee.shanel.ideabucket.model.LoginRequest;
import ee.shanel.ideabucket.security.AuthenticationService;
import ee.shanel.ideabucket.service.LoginService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest
{
    private static final String TOKEN = "TOKEN";

    private static final String EMAIL = "test@example.com";

    private static final String INVALID_EMAIL = "54";

    @Mock
    private AuthenticationService mockAuthenticationService;

    @Mock
    private LoginService mockLoginService;

    private LoginController subject;

    @BeforeEach
    void beforeEach()
    {
        subject = new LoginController(
                mockAuthenticationService,
                mockLoginService
        );
    }

    @Test
    void itHandlesValidEmail()
    {
        final var request = LoginRequest.builder()
                .email(EMAIL)
                .build();

        Mockito.when(mockLoginService.login(Mockito.any()))
                .thenReturn(Mono.just(Boolean.TRUE));

        StepVerifier.create(subject.login(request))
                .assertNext(res -> Assertions.assertTrue(res.getStatusCode().is2xxSuccessful()))
                .verifyComplete();

        Mockito.verify(mockLoginService).login(request);
    }

    @Test
    void itFailsToLogin()
    {
        final var request = LoginRequest.builder()
                .email(EMAIL)
                .build();

        Mockito.when(mockLoginService.login(Mockito.any()))
                .thenReturn(Mono.just(Boolean.FALSE));

        StepVerifier.create(subject.login(request))
                .assertNext(res -> Assertions.assertTrue(res.getStatusCode().is4xxClientError()))
                .verifyComplete();

        Mockito.verify(mockLoginService).login(request);
    }


    @Test
    void itHandlesAnInvalidEmail()
    {
        StepVerifier.create(subject.login(LoginRequest.builder().email(INVALID_EMAIL).build()))
                .assertNext(res -> Assertions.assertTrue(res.getStatusCode().is4xxClientError()))
                .verifyComplete();
    }

    @Test
    void itHandlesMissingEmail()
    {
        StepVerifier.create(subject.login(LoginRequest.builder().build()))
                .assertNext(res -> Assertions.assertTrue(res.getStatusCode().is4xxClientError()))
                .verifyComplete();
    }

    @Test
    void itHasExpired()
    {
        Mockito.when(mockAuthenticationService.authenticate(Mockito.anyString()))
                .thenReturn(Mono.error(new BadCredentialsException("")));

        StepVerifier.create(subject.isLoginExpired(TOKEN))
                .assertNext(res -> Assertions.assertAll(() ->
                {
                    Assertions.assertTrue(res.getStatusCode().is2xxSuccessful());
                    Assertions.assertNotNull(res.getBody());
                    Assertions.assertTrue(res.getBody());
                }))
                .verifyComplete();

        Mockito.verify(mockAuthenticationService).authenticate(TOKEN);
    }

    @Test
    void itHasntExpired()
    {
        Mockito.when(mockAuthenticationService.authenticate(Mockito.anyString()))
                .thenReturn(Mono.just(new UsernamePasswordAuthenticationToken(null, null)));

        StepVerifier.create(subject.isLoginExpired(TOKEN))
                .assertNext(res -> Assertions.assertAll(() ->
                    {
                        Assertions.assertTrue(res.getStatusCode().is2xxSuccessful());
                        Assertions.assertNotNull(res.getBody());
                        Assertions.assertFalse(res.getBody());
                    }))
                .verifyComplete();

        Mockito.verify(mockAuthenticationService).authenticate(TOKEN);
    }


}
