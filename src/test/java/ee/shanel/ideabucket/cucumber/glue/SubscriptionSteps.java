package ee.shanel.ideabucket.cucumber.glue;

import ee.shanel.ideabucket.model.SubscriptionSubmission;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SubscriptionSteps
{
    private final TestTokenSteps testTokenSteps;

    private final WebTestClient webClient;

    @Given("^the user with token (.*) subscribes$")
    public void theFollowingSubscription(final String token, final SubscriptionSubmission submission)
    {
        webClient.post()
                .uri("/subscribe")
                .headers(val -> val.setBearerAuth(testTokenSteps.getToken(token)))
                .body(Mono.just(submission), SubscriptionSubmission.class)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }
}
