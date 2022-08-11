package ee.shanel.ideabucket.factory;

import ee.shanel.ideabucket.config.DeployProperties;
import ee.shanel.ideabucket.config.EmailProperties;
import ee.shanel.ideabucket.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationConfirmationEmailFactory implements TokenEmailFactory
{
    private final DeployProperties deployProperties;

    private final EmailProperties emailProperties;

    public MailMessage create(final User user, final String token)
    {
        final var mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(emailProperties.getSender());
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Registration Confirmation");
        mailMessage.setText(String.format(
                "Hello, %s!\n Thanks for registering for IdeaBucket"
                + "\nAccess your account here: %s/token?token=%s",
                user.getName(),
                deployProperties.getHost(),
                token)
        );

        return mailMessage;
    }
}
