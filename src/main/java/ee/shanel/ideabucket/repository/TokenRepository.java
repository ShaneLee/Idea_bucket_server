package ee.shanel.ideabucket.repository;

import reactor.core.publisher.Mono;

public interface TokenRepository
{
    Mono<String> put(String id, String token);

    Mono<String> get(String id);

    Mono<String> getByEmail(String email);

    Mono<String> delete(String id);

    Mono<Boolean> deleteAll();
}

