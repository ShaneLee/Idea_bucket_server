package ee.shanel.ideabucket.factory;

import ee.shanel.ideabucket.config.EmailProperties;
import ee.shanel.ideabucket.model.support.Support;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SupportEmailFactory
{
    private final EmailProperties emailProperties;

    public MailMessage create(final Support support)
    {
        final var mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(emailProperties.getSender());
        mailMessage.setTo(emailProperties.getSupportDestination());
        mailMessage.setSubject(String.format("Support Request Received %s", support.getId()));
        // TODO
        mailMessage.setText(String.format(
                support.toString()
        ));

        return mailMessage;
    }
}
