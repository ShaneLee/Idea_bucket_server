package ee.shanel.ideabucket.cucumber.glue;

import ee.shanel.ideabucket.generator.TestIdGenerator;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TestIdSteps
{
    private final TestIdGenerator idGenerator;

    @Before
    public void beforeEachScenario()
    {
        idGenerator.clear();
    }

    @Given("^the id generator returns the following ids in order$")
    public void theIdGeneratorReturnsTheFollowing(final List<String> ids)
    {
        idGenerator.addAll(ids);
    }
}
