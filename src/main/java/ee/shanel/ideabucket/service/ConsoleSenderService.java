package ee.shanel.ideabucket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty("sender.console")
public class ConsoleSenderService implements SenderService
{
    @Override
    public Mono<Void> send(final String userEmail, final String token)
    {
        LOG.info("Login: http://localhost:3000/token?token={}", token);
        return Mono.empty();
    }
}
