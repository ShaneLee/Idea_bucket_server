package ee.shanel.ideabucket.controller;

import ee.shanel.ideabucket.service.LogoutService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class LogoutControllerTest
{
    private static final String TOKEN = "TEST_TOKEN";

    @Mock
    private LogoutService mockLogoutService;

    private LogoutController subject;

    @BeforeEach
    void beforeEach()
    {
        subject = new LogoutController(
                mockLogoutService
        );
    }

    @Test
    void itLogsOut()
    {
        final Authentication authentication = new UsernamePasswordAuthenticationToken(TOKEN, null);

        Mockito.when(mockLogoutService.logout(Mockito.any()))
                .thenReturn(Mono.just(Boolean.TRUE));

        StepVerifier.create(subject.logout(authentication))
                .assertNext(res -> Assertions.assertTrue(res.getStatusCode().is2xxSuccessful()))
                .verifyComplete();

        Mockito.verify(mockLogoutService).logout(TOKEN);
    }

    @Test
    void itFailsToLogout()
    {
        final Authentication authentication = new UsernamePasswordAuthenticationToken(TOKEN, null);

        Mockito.when(mockLogoutService.logout(Mockito.any()))
                .thenReturn(Mono.just(Boolean.FALSE));

        StepVerifier.create(subject.logout(authentication))
                .assertNext(res -> Assertions.assertTrue(res.getStatusCode().is4xxClientError()))
                .verifyComplete();

        Mockito.verify(mockLogoutService).logout(TOKEN);
    }
}
