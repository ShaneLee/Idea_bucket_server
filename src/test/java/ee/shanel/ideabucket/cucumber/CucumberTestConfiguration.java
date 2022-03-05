package ee.shanel.ideabucket.cucumber;

import ee.shanel.ideabucket.generator.IdGenerator;
import ee.shanel.ideabucket.generator.TestIdGenerator;
import ee.shanel.ideabucket.service.SenderService;
import ee.shanel.ideabucket.service.SenderServiceCapturer;
import lombok.RequiredArgsConstructor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.ReactiveMongoClientFactoryBean;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;

import static ee.shanel.ideabucket.cucumber.CucumberIT.CONTAINER;

@TestConfiguration
@RequiredArgsConstructor
public class CucumberTestConfiguration
{
    @Primary
    @Bean
    Clock testClock()
    {
        return Clock.fixed(Instant.parse("2022-01-01T10:30:00.000Z"), ZoneId.of("UTC"));
    }

    @Primary
    @Bean
    SenderService senderService(final SenderService senderService)
    {
        return new SenderServiceCapturer(new HashMap<>(), senderService);
    }

    @Primary
    @Bean
    IdGenerator idGenerator()
    {
        return new TestIdGenerator();
    }

    @Primary
    @Bean
    ReactiveMongoClientFactoryBean testMongoClientFactory()
    {
        final var mongo = new ReactiveMongoClientFactoryBean();
        mongo.setHost(CONTAINER.getHost());
        mongo.setPort(CONTAINER.getMappedPort(27017));
        return mongo;
    }

    @Primary
    @Bean
    JavaMailSender JavaMailSenderMock()
    {
        return Mockito.mock(JavaMailSender.class);
    }
}
