package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.repository.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class DefaultLogoutServiceTest
{
    private static final String USER_ID = "ID";

    @Mock
    private TokenRepository mockTokenRepository;

    private DefaultLogoutService subject;

    @BeforeEach
    void setUp()
    {
        subject = new DefaultLogoutService(mockTokenRepository);
    }

    @Test
    void itLogsOut()
    {
        Mockito.when(mockTokenRepository.deleteByUserId(Mockito.anyString()))
                .thenReturn(Mono.just(1L));

        StepVerifier.create(subject.logout(USER_ID))
                .expectNext(Boolean.TRUE)
                .verifyComplete();

        Mockito.verify(mockTokenRepository).deleteByUserId(USER_ID);
    }
}
