package ee.shanel.ideabucket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnMissingBean(ConsoleSenderService.class)
public class EmailSenderService implements SenderService
{
    private final JavaMailSender mailSender;

    @Override
    public Mono<Void> send(final MailMessage message)
    {
        return Mono.fromRunnable(() -> mailSender.send((SimpleMailMessage) message));
    }
}
