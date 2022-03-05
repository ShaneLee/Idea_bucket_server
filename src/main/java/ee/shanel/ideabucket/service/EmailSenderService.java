package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.config.EmailProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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

    private final EmailProperties emailProperties;

    @Override
    public Mono<Void> send(final String userEmail, final String token)
    {
        final var mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(emailProperties.getSender());
        mailMessage.setTo(userEmail);
        mailMessage.setSubject("Your login link");
        mailMessage.setText(String.format(
            "Hello!%nAccess your account here: http://localhost:3000/token?token=%s/%s",
            token,
            userEmail));

        return Mono.fromRunnable(() -> mailSender.send(mailMessage));
    }
}
