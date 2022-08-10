package ee.shanel.ideabucket.cucumber.glue;

import ee.shanel.ideabucket.model.Idea;
import ee.shanel.ideabucket.repository.IdeaRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class IdeasSteps
{
    private final IdeaRepository ideaRepository;

    private final TestTokenSteps testTokenSteps;

    private final WebTestClient webClient;

    private Flux<Idea> result;

    @Before
    public void beforeEachScenario()
    {
        ideaRepository.deleteAll()
                .as(StepVerifier::create)
                .thenConsumeWhile(Objects::nonNull)
                .verifyComplete();

        result = null;
    }

    @When("^the following ideas? (?:is|are) saved via REST with the token (.*)$")
    public void ideasAreSavedViaRest(final String token, final List<Idea> ideas)
    {
        ideas.forEach(idea -> webClient.post()
                .uri("/rest/v1/submitIdea")
                .headers(val -> val.setBearerAuth(testTokenSteps.getToken(token)))
                .body(Mono.just(idea), Idea.class)
                .exchange()
                .returnResult(Idea.class)
                .getResponseBody()
                .as(StepVerifier::create)
                .thenConsumeWhile(Objects::nonNull)
                .verifyComplete());
    }

    @When("^the following ideas is saved via REST with the token (.*) and receives the status (.*)$")
    public void ideasAreSavedViaRest(final String token, final Idea idea, final Integer status)
    {
        webClient.post()
                .uri("/rest/v1/submitIdea")
                .headers(val -> val.setBearerAuth(testTokenSteps.getToken(token)))
                .body(Mono.just(idea), Idea.class)
                .exchange()
                .expectStatus()
                .isEqualTo(status);
    }

    @When("^the user requests the ideas endpoint with the token (.*)$")
    public void requestEndpoint(final String token)
    {
        result = webClient.get()
                .uri("/rest/v1/ideas")
                .headers(val -> val.setBearerAuth(testTokenSteps.getToken(token)))
                .exchange()
                .returnResult(Idea.class)
                .getResponseBody();
    }

    @When("^the user requests the ideas endpoint with the token (.*) and category (.*)$")
    public void requestEndpoint(final String token, final String category)
    {
        result = webClient.get()
                .uri(String.format("/rest/v1/ideas?category=%s", category))
                .headers(val -> val.setBearerAuth(testTokenSteps.getToken(token)))
                .exchange()
                .returnResult(Idea.class)
                .getResponseBody();
    }

    @Then("^the following ideas are returned$")
    public void thenTheFollowingAccountSettingsAreReturned(final List<Idea> ideas)
    {
        StepVerifier.create(result.collectList())
                .assertNext(res -> assertIdeas(ideas, res))
                .verifyComplete();
    }

    private static void assertIdeas(final List<Idea> expected, final List<Idea> result)
    {
        final var map = expected.stream()
                .collect(Collectors.toMap(Idea::getIdea, Function.identity()));

        result.forEach(res -> assertIdea(map.get(res.getIdea()), res));
    }

    private static void assertIdea(final Idea expected, final Idea result)
    {
        Assertions.assertAll(() ->
        {
            Assertions.assertEquals(expected.getIdea(), result.getIdea());
            Assertions.assertEquals(expected.getCategory(), result.getCategory());
        });

    }
}
