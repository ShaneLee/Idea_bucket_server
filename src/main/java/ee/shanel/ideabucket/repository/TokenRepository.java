package ee.shanel.ideabucket.repository;

import ee.shanel.ideabucket.model.Token;
import ee.shanel.ideabucket.model.entity.TokenEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface TokenRepository extends ReactiveCrudRepository<TokenEntity, String>
{
    Mono<Token> findByEmail(String email);

    Mono<Token> findByUserId(String userId);

    Mono<Long> deleteByToken(String token);

    Mono<Boolean> existsByToken(String token);
}

