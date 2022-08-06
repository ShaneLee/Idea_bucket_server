package ee.shanel.ideabucket.cucumber.glue;

import ee.shanel.ideabucket.model.LoginRequest;
import ee.shanel.ideabucket.model.MailingList;
import ee.shanel.ideabucket.model.TestUser;
import ee.shanel.ideabucket.model.entity.TokenEntity;
import ee.shanel.ideabucket.repository.TokenRepository;
import ee.shanel.ideabucket.repository.UserRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationSteps
{
    private final TokenRepository tokenRepository;

    private final TestTokenSteps testTokenSteps;

    private final UserRepository userRepository;

    private final WebTestClient webClient;

    @Before
    public void beforeEachScenario()
    {
        tokenRepository.deleteAll();
    }

    @Given("^the following users? (?:is|are) registered$")
    public void theFollowingUser(final List<TestUser> users)
    {
        StepVerifier.create(Flux.fromIterable(users)
                .map(val -> TokenEntity.builder().token(val.getToken()).email(val.getEmail()).build())
                .flatMap(tokenRepository::save))
                .thenConsumeWhile(Objects::nonNull)
                .verifyComplete();
    }

    @When("^the authenticated users? logout$")
    public void theUsersLogout()
    {
        userRepository.findAll()
                .map(user -> webClient.get()
                .uri("/rest/v1/logout")
                .headers(val -> val.setBearerAuth(user.getToken()))
                .exchange().expectStatus()
                .is2xxSuccessful())
                .collectList()
                .block();
    }

    @When("^the following anonymous users? logout$")
    public void theAnonymousUsersLogout(final List<TestUser> users)
    {
        users.forEach(user -> webClient.get()
                .uri("/rest/v1/logout")
                .headers(val -> val.setBearerAuth(user.getToken()))
                .exchange().expectStatus()
                .isUnauthorized());
    }

    @When("^the users? (?:request|requests) the (.*) endpoint with the method (.*) the status is (.*)$")
    public void requestEndpoint(final String endpoint,
                                final HttpMethod method,
                                final Integer status,
                                final List<TestUser> users)
    {
        users.forEach(user -> webClient.method(method)
                .uri(endpoint)
                .headers(val -> val.setBearerAuth(testTokenSteps.getToken(user.getToken())))
                .exchange()
                .expectStatus()
                .isEqualTo(status));
    }

    @When("^the users? (?:request|requests) the mailing list endpoint the status is (.*)$")
    public void requestEndpoint(final Integer status, final List<TestUser> users)
    {
        users.forEach(user -> webClient.post()
                .uri("/rest/v1/mailingList")
                .headers(val -> val.setBearerAuth(testTokenSteps.getToken(user.getToken())))
                .body(Mono.just(MailingList.builder().email(user.getEmail()).build()), MailingList.class)
                .exchange().expectStatus()
                .isEqualTo(status));
    }

    @Then("^the following login token request receives a valid token$")
    public void theFollowingLoginTokenRequest(final LoginRequest loginRequest)
    {
        webClient.post()
                .uri("/rest/v1/login")
                .body(Mono.just(loginRequest), LoginRequest.class)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        testTokenSteps.captureToken(loginRequest.getEmail());
    }

    @Then("^the following login token request receives the status (.*)$")
    public void theFollowingLoginTokenRequest(final Integer status, final LoginRequest loginRequest)
    {
        webClient.post()
                .uri("/rest/v1/login")
                .body(Mono.just(loginRequest), LoginRequest.class)
                .exchange()
                .expectStatus()
                .isEqualTo(status);
    }

}
