package ee.shanel.ideabucket.cucumber.glue;

import com.mongodb.assertions.Assertions;
import ee.shanel.ideabucket.repository.TokenRepository;
import ee.shanel.ideabucket.service.TokenEmailFactoryCapturer;
import ee.shanel.ideabucket.utils.TokenUtils;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class TestTokenSteps
{
    private final TokenEmailFactoryCapturer tokenEmailFactoryCapturer;

    private final TokenRepository tokenRepository;

    private Map<String, String> testTokenByReceived;

    @Before
    public void beforeEachScenario()
    {
        testTokenByReceived = new HashMap<>();
        tokenEmailFactoryCapturer.clear();
    }

    @When("^the token (.*) is deleted$")
    public void theTokenIsDeleted(final String token)
    {
        tokenRepository.deleteByToken(testTokenByReceived.get(token))
                .as(StepVerifier::create)
                .thenConsumeWhile(Objects::nonNull)
                .verifyComplete();

    }

    @Then("^the user (.*) receives a token$")
    public void theUserReceivesAToken(final String id)
    {
        testTokenByReceived.put(
            TokenUtils.getReceived(),
            Objects.requireNonNull(tokenEmailFactoryCapturer.getTokenFromEmail(id))
        );
    }

    @Then("^the user (.*) doesn't receive a token$")
    public void theUserDoesntReceivesAToken(final String id)
    {
        Assertions.assertNull(tokenEmailFactoryCapturer.getTokenFromEmail(id));
    }

    @Then("^the user (.*) receives a token with key (.*)$")
    public void theUserReceivesAToken(final String id, final String key)
    {
        testTokenByReceived.put(
            key,
            Objects.requireNonNull(tokenEmailFactoryCapturer.getTokenFromEmail(id))
        );
    }

    public void captureToken(final String value)
    {
        testTokenByReceived.put(
            TokenUtils.getReceived(),
            Objects.requireNonNull(tokenEmailFactoryCapturer.getTokenFromEmail(value))
        );
    }

    public String getToken(final String token)
    {
        return testTokenByReceived.get(token);
    }
}
