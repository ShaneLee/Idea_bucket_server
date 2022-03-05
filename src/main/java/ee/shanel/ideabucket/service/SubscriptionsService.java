package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.model.SubscriptionSubmission;
import reactor.core.publisher.Mono;

public interface SubscriptionsService
{
    Mono<Boolean> subscribe(String userToken, SubscriptionSubmission submission);
}
