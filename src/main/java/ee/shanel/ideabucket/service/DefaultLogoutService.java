package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DefaultLogoutService implements LogoutService
{
    private final TokenRepository repository;

    @Override
    public Mono<Boolean> logout(final String token)
    {
        return repository.delete(token)
                .thenReturn(Boolean.TRUE);
    }
}
