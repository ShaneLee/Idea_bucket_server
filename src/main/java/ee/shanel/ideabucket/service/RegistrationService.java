package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.model.Registration;
import ee.shanel.ideabucket.model.User;
import reactor.core.publisher.Mono;

public interface RegistrationService
{
    Mono<User> register(Registration registration);
}
