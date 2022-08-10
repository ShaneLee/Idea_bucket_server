package ee.shanel.ideabucket.cucumber.glue;

import ee.shanel.ideabucket.model.SubscriptionSubmission;
import ee.shanel.ideabucket.model.Token;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

@RequiredArgsConstructor
public class SubscriptionSteps
{
    private final TestTokenSteps testTokenSteps;

    private final WebTestClient webClient;

    @Given("^the user with token (.*) subscribes and receives the token (.*)$")
    public void theFollowingSubscription(final String token,
                                         final String newTokenKey,
                                         final SubscriptionSubmission submission)
    {
        webClient.post()
                .uri("/rest/v1/subscribe")
                .headers(val -> val.setBearerAuth(testTokenSteps.getToken(token)))
                .body(Mono.just(submission), SubscriptionSubmission.class)
                .exchange()
                .returnResult(Token.class)
                .getResponseBody()
                .doOnNext(val -> testTokenSteps.saveToken(newTokenKey, val.getToken()))
                .as(StepVerifier::create)
                .thenConsumeWhile(Objects::nonNull)
                .verifyComplete();
    }
}
