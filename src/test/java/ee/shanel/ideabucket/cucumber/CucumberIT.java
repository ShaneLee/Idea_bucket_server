package ee.shanel.ideabucket.cucumber;

import ee.shanel.ideabucket.IdeaBucketApplication;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.GenericContainer;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    tags = "not @tdd",
    plugin = {"pretty", "json:target/cucumber.json"})
@SpringBootTest(classes = {
    IdeaBucketApplication.class,
    CucumberTestConfiguration.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@CucumberContextConfiguration
@TestPropertySource(locations = "classpath:/test-application.properties")
@ActiveProfiles("cucumber")
public final class CucumberIT
{
    private static final String IMAGE = "mongo:5.0.6";

    @ClassRule
    public static final GenericContainer<?> CONTAINER = new GenericContainer<>(IMAGE)
            .withExposedPorts(27017);

    private CucumberIT()
    {
    }
}
