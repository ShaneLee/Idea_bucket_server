package ee.shanel.ideabucket.service;

import reactor.core.publisher.Mono;

public interface SenderService
{
    Mono<Void> send(String userId, String token);
}
