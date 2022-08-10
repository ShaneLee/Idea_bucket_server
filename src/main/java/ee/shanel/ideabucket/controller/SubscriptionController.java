package ee.shanel.ideabucket.controller;

import ee.shanel.ideabucket.model.SubscriptionSubmission;
import ee.shanel.ideabucket.model.Token;
import ee.shanel.ideabucket.service.SubscriptionsService;
import ee.shanel.ideabucket.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SubscriptionController
{
    private final SubscriptionsService subscriptionsService;

    private final UserService userService;

    /**
     * Subscribes a user.
     *
     * @param authentication the authentication
     * @param subscriptionSubmission the subscription submission
     * @return the new token if successful
     */
    @PostMapping("/rest/v1/subscribe")
    public Mono<ResponseEntity<Token>> subscribe(
            final Authentication authentication,
            @RequestBody final SubscriptionSubmission subscriptionSubmission)
    {
        return Mono.just(subscriptionSubmission)
                .flatMap(val -> userService.findUser(authentication.getName()))
                .flatMap(val -> subscriptionsService.subscribe(val, subscriptionSubmission))
                .doOnNext(val -> LOG.info("User subscribed"))
                .map(val -> Token.builder().token(val).build())
                .flatMap(val -> Mono.just(ResponseEntity.ok().body(val)));
    }
}
