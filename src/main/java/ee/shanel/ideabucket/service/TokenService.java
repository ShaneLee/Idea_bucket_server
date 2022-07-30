package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.model.User;
import reactor.core.publisher.Mono;

public interface TokenService
{
    Mono<String> create(User id);

    Mono<String> getByUserId(String id);

    Mono<String> getByEmail(String email);

    Mono<Boolean> existsByToken(String token);
}
