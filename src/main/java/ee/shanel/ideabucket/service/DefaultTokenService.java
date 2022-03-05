package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.factory.TokenFactory;
import ee.shanel.ideabucket.model.User;
import ee.shanel.ideabucket.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DefaultTokenService implements TokenService
{
    private final TokenFactory tokenFactory;

    private final TokenRepository tokenRepository;

    @Override
    public Mono<String> create(final User user)
    {
        return Mono.just(user)
                .map(tokenFactory::create)
                .flatMap(val -> tokenRepository.put(user.getId(), val));
    }

    @Override
    public Mono<String> get(final String id)
    {
        return tokenRepository.get(id);
    }

    @Override
    public Mono<String> getByEmail(final String id)
    {
        return tokenRepository.getByEmail(id);
    }
}
