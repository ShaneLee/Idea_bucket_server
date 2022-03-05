package ee.shanel.ideabucket.controller;

import ee.shanel.ideabucket.model.SubscriptionSubmission;
import ee.shanel.ideabucket.model.authentication.IdeaBucketRole;
import ee.shanel.ideabucket.service.SubscriptionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/subscribe")
    public Mono<ResponseEntity<Object>> subscribe(
            final Authentication authentication,
            @RequestBody final SubscriptionSubmission subscriptionSubmission)
    {
        return Mono.just(subscriptionSubmission)
                .flatMap(val -> subscriptionsService.subscribe(authentication.getName(), subscriptionSubmission))
                .doOnNext(val -> LOG.info("User subscribed"))
                .flatMap(val -> Mono.just(ResponseEntity.ok().build()));
    }

    @GetMapping("/only")
    public Mono<ResponseEntity<String>> only(final Authentication authentication)
    {
        return Mono.just(authentication)
                .filter(val -> val.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .filter(res -> StringUtils.contains(IdeaBucketRole.standard().getUserRole(), res))
                        .findFirst()
                        .map(res -> Boolean.TRUE)
                        .orElse(Boolean.FALSE))
                .map(val -> ResponseEntity.ok("Hello world!"))
                .defaultIfEmpty(ResponseEntity.status(401).body(""));
    }
}
