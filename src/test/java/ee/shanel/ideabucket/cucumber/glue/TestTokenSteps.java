package ee.shanel.ideabucket.cucumber.glue;

import ee.shanel.ideabucket.service.SenderServiceCapturer;
import ee.shanel.ideabucket.utils.TokenUtils;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class TestTokenSteps
{
    private final SenderServiceCapturer senderServiceCapturer;

    private Map<String, String> testTokenByReceived;

    @Before
    public void beforeEachScenario()
    {
        testTokenByReceived = new HashMap<>();
        senderServiceCapturer.clear();
    }

    @Then("^the user (.*) receives a token$")
    public void theUserReceivesAToken(final String id)
    {
        testTokenByReceived.put(
            TokenUtils.getReceived(),
            Objects.requireNonNull(senderServiceCapturer.getTokenFromEmail(id))
        );
    }

    @Then("^the user (.*) receives a token with key (.*)$")
    public void theUserReceivesAToken(final String id, final String key)
    {
        testTokenByReceived.put(
            key,
            Objects.requireNonNull(senderServiceCapturer.getTokenFromEmail(id))
        );
    }

    public void captureToken(final String value)
    {
        testTokenByReceived.put(
            TokenUtils.getReceived(),
            Objects.requireNonNull(senderServiceCapturer.getTokenFromEmail(value))
        );
    }

    public String getToken(final String token)
    {
        return testTokenByReceived.get(token);
    }
}
