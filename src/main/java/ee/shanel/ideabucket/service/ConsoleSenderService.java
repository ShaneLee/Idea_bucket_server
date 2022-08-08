package ee.shanel.ideabucket.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.MailMessage;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty("sender.console")
public class ConsoleSenderService implements SenderService
{
    @Override
    @SneakyThrows
    public Mono<Void> send(final MailMessage message)
    {
        final MimeMessage msg = (MimeMessage) message;
        LOG.info("Login: http://localhost:3000/token?token={}", msg.getContent().toString());
        return Mono.empty();
    }
}
