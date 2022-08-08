package ee.shanel.ideabucket.cucumber.glue;

import ee.shanel.ideabucket.model.entity.SupportEntity;
import ee.shanel.ideabucket.model.support.Support;
import ee.shanel.ideabucket.repository.SupportRepository;
import ee.shanel.ideabucket.service.SenderService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.sql.Date;
import java.time.Clock;
import java.time.Instant;
import java.util.Objects;

@RequiredArgsConstructor
public class SupportSteps
{
    private final Clock clock;

    private final SenderService senderServiceSpy;

    private final SupportRepository supportRepository;

    private final TestTokenSteps testTokenSteps;

    private final WebTestClient webClient;

    @Before
    public void beforeEachScenario()
    {
        supportRepository.deleteAll()
                .as(StepVerifier::create)
                .thenConsumeWhile(Objects::nonNull)
                .verifyComplete();
    }

    @When("^the user with token (.*) sends the following support request$")
    public void userSendsASupportRequest(final String token, final Support support)
    {
        webClient.post()
                .uri("/rest/v1/supportRequest")
                .headers(val -> val.setBearerAuth(testTokenSteps.getToken(token)))
                .body(Mono.just(support), Support.class)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Then("^the following support request is found in the repository$")
    public void theFollowingSupportRequestIsFoundInTheRepository(final SupportEntity support)
    {
        final var expected = support.toBuilder()
                .timeReceived(Date.from(Instant.now(clock)))
                .build();

        supportRepository.findById(support.getId())
                .as(StepVerifier::create)
                .assertNext(res -> Assertions.assertEquals(expected, res))
                .verifyComplete();
    }

    @Then("^the following support request is sent$")
    public void theFollowingSupportRequestIsSent(final Support support)
    {
        Mockito.verify(senderServiceSpy)
                .send(Mockito.argThat((SimpleMailMessage arg) -> Objects.requireNonNull(arg.getText())
                        .contains(support.getBody())));
    }
}
