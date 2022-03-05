package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.model.LoginRequest;
import reactor.core.publisher.Mono;

public interface LoginService
{
    Mono<Boolean> login(LoginRequest request);
}
