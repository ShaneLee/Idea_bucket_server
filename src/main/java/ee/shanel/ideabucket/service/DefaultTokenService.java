package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.factory.TokenFactory;
import ee.shanel.ideabucket.model.Token;
import ee.shanel.ideabucket.model.User;
import ee.shanel.ideabucket.model.entity.TokenEntity;
import ee.shanel.ideabucket.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DefaultTokenService implements TokenService
{
    private final ConversionService conversionService;

    private final TokenFactory tokenFactory;

    private final TokenRepository tokenRepository;

    @Override
    public Mono<String> create(final User user)
    {
        return Mono.just(user)
                .map(tokenFactory::create)
                .mapNotNull(val -> conversionService.convert(val, TokenEntity.class))
                .flatMap(tokenRepository::save)
                .map(TokenEntity::getToken);
    }

    @Override
    public Mono<String> getByUserId(final String id)
    {
        return tokenRepository.findById(id)
                .map(TokenEntity::getToken);
    }

    @Override
    public Mono<String> getByEmail(final String id)
    {
        return tokenRepository.findByEmail(id)
                .map(Token::getToken);
    }

    @Override
    public Mono<Boolean> existsByToken(final String token)
    {
        return tokenRepository.existsByToken(token);
    }
}
