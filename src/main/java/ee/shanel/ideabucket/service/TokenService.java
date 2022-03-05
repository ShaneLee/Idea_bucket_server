package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.model.User;
import reactor.core.publisher.Mono;

public interface TokenService
{
    Mono<String> create(User id);

    Mono<String> get(String id);

    Mono<String> getByEmail(String email);
}
