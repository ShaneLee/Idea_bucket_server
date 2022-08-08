package ee.shanel.ideabucket.service;

import org.springframework.mail.MailMessage;
import reactor.core.publisher.Mono;

public interface SenderService
{
    Mono<Void> send(MailMessage email);
}
