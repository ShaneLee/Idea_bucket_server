package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.model.Registration;
import ee.shanel.ideabucket.model.User;
import reactor.core.publisher.Mono;

public interface UserService
{
    Mono<User> put(User user);

    Mono<Boolean> exists(String email);

    Mono<User> findUser(String token);

    Mono<User> findUserByEmail(String email);

    Mono<User> register(Registration registration);
}
