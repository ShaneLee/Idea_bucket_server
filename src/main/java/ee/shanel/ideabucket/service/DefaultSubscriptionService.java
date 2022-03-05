package ee.shanel.ideabucket.service;

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
    private final PaymentsService paymentsService;

    private final SenderService senderService;

    private final UserService userService;

    @Override
    public Mono<Boolean> subscribe(final String userToken, final SubscriptionSubmission submission)
    {
        return userService.findUser(userToken)
                .flatMap(val -> process(val, submission))
                .map(User::withStandard)
                .flatMap(userService::put)
                .flatMap(this::sendConfirmation)
                .defaultIfEmpty(Boolean.FALSE);
    }

    private Mono<Boolean> sendConfirmation(final User user)
    {
        return senderService.send(user.getId(), "")
                .thenReturn(Boolean.TRUE);
    }

    private Mono<User> process(final User user, final SubscriptionSubmission submission)
    {
        return Mono.just(user)
                .filter(User::isUserStandard)
                .flatMap(val -> paymentsService.process(submission)
                        .filter(Boolean.TRUE::equals)
                        .map(res -> user)
                        .defaultIfEmpty(user))
                .switchIfEmpty(Mono.defer(() -> Mono.just(user)
                        .doOnNext(val -> LOG.info("User already subscribed {}", val))));
    }
}
