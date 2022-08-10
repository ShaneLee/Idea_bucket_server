package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.model.SubscriptionSubmission;
import reactor.core.publisher.Mono;

public interface PaymentsValidationService
{
    Mono<Boolean> validate(SubscriptionSubmission submission);
}
