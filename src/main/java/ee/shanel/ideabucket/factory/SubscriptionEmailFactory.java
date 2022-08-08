package ee.shanel.ideabucket.factory;

import ee.shanel.ideabucket.config.EmailProperties;
import ee.shanel.ideabucket.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionEmailFactory
{
    private final EmailProperties emailProperties;

    public MailMessage create(final User user)
    {
        final var mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(emailProperties.getSender());
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Subscription confirmation");
        mailMessage.setText(String.format(
                "Hello!%nAccess your account here: http://localhost:3000/login",
                user.getEmail())
        );

        return mailMessage;
    }
}
