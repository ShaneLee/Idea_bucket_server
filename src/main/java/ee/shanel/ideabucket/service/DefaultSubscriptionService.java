package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.factory.SubscriptionEmailFactory;
import ee.shanel.ideabucket.model.SubscriptionSubmission;
import ee.shanel.ideabucket.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultSubscriptionService implements SubscriptionsService
{
    private final PaymentsValidationService paymentsValidationService;

    private final SenderService senderService;

    private final SubscriptionEmailFactory factory;

    private final TokenService tokenService;

    private final UserService userService;

    @Override
    public Mono<String> subscribe(final User user, final SubscriptionSubmission submission)
    {
        return process(user, submission)
                .flatMap(tokenService::refreshToken)
                .flatMap(userService::put)
                .flatMap(this::sendConfirmation)
                .defaultIfEmpty(user.getToken());
    }

    private Mono<String> sendConfirmation(final User user)
    {
        return Mono.just(user)
                .map(factory::create)
                .flatMap(val -> senderService.send(val)
                        .thenReturn(user.getToken()));
    }

    private Mono<User> process(final User user, final SubscriptionSubmission submission)
    {
        return Mono.just(user)
                .filter(User::isUserStandard)
                .flatMap(val -> paymentsValidationService.validate(submission)
                        .filter(Boolean.TRUE::equals)
                        .map(res -> user)
                        .map(User::withSubscribed)
                        .defaultIfEmpty(user))
                .switchIfEmpty(Mono.defer(() -> Mono.just(user)
                        .doOnNext(val -> LOG.info("User already subscribed {}", val))));
    }
}
