package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.model.SubscriptionSubmission;
import ee.shanel.ideabucket.model.User;
import reactor.core.publisher.Mono;

public interface SubscriptionsService
{
    Mono<String> subscribe(User user, SubscriptionSubmission submission);
}
