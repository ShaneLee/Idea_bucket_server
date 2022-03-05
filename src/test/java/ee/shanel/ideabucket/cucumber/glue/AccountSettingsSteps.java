package ee.shanel.ideabucket.cucumber.glue;

import ee.shanel.ideabucket.model.TestUser;
import ee.shanel.ideabucket.model.entity.AccountSettingsEntity;
import ee.shanel.ideabucket.model.settings.AccountSettings;
import ee.shanel.ideabucket.repository.AccountSettingsRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
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

@Slf4j
@RequiredArgsConstructor
public class AccountSettingsSteps
{
    private final AccountSettingsRepository accountSettingsRepository;

    private final TestTokenSteps testTokenSteps;

    private final WebTestClient webClient;

    private AccountSettings result;

    @Before
    public void beforeEachScenario()
    {
        accountSettingsRepository.deleteAll()
                .as(StepVerifier::create)
                .thenConsumeWhile(Objects::nonNull)
                .verifyComplete();

        result = null;
    }

    @Given("^the following account settings are saved$")
    public void accountSettingsAreSaved(final List<AccountSettingsEntity> settingEntities)
    {
        Flux.fromIterable(settingEntities)
                .flatMap(accountSettingsRepository::save)
                .as(StepVerifier::create)
                .thenConsumeWhile(Objects::nonNull)
                .verifyComplete();
    }

    @When("^the following account settings are saved via REST with the token (.*)$")
    public void accountSettingsAreSavedViaRest(final String token, final List<AccountSettings> settings)
    {
        settings.forEach(setting -> webClient.put()
                .uri("/rest/v1/accountSettings")
                .headers(val -> val.setBearerAuth(testTokenSteps.getToken(token)))
                .body(Mono.just(setting), AccountSettings.class)
                .exchange()
                .returnResult(AccountSettings.class)
                .getResponseBody()
                .as(StepVerifier::create)
                .thenConsumeWhile(Objects::nonNull)
                .verifyComplete());
    }

    @When("^the user requests the account settings endpoint$")
    public void requestEndpoint(final TestUser user)
    {
        webClient.get()
                .uri("/rest/v1/accountSettings")
                .headers(val -> val.setBearerAuth(testTokenSteps.getToken(user.getToken())))
                .exchange()
                .returnResult(AccountSettings.class)
                .getResponseBody()
                .doOnNext(val -> result = val)
                .as(StepVerifier::create)
                .thenConsumeWhile(Objects::nonNull)
                .verifyComplete();
    }

    @Then("^the following account settings are returned$")
    public void thenTheFollowingAccountSettingsAreReturned(final AccountSettings accountSettings)
    {
        Assertions.assertEquals(accountSettings, result);
    }
}
