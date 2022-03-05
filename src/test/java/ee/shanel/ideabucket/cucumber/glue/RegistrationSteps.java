package ee.shanel.ideabucket.cucumber.glue;

import ee.shanel.ideabucket.model.Registration;
import ee.shanel.ideabucket.repository.UserRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class RegistrationSteps
{
    private final UserRepository userRepository;

    private final WebTestClient webClient;

    @Before
    public void beforeEachScenario()
    {
        userRepository.deleteAll()
                .block();
    }

    @Given("^the following registration requests? (?:is|are) received with a (.*) status$")
    public void theFollowingRegistrations(final Integer status, final List<Registration> registrations)
    {
        registrations.forEach(registration -> webClient.post()
                .uri("/rest/v1/register")
                .body(Mono.just(registration), Registration.class)
                .exchange()
                .expectStatus()
                .isEqualTo(status));
    }
}
