package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.model.User;
import ee.shanel.ideabucket.model.support.Support;
import reactor.core.publisher.Mono;

public interface SupportService
{
    Mono<Support> process(Support support, User user);
}
