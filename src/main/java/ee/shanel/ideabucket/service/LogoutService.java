package ee.shanel.ideabucket.service;

import reactor.core.publisher.Mono;

public interface LogoutService
{
    Mono<Boolean> logout(String token);
}
