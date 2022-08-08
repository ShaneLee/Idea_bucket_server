package ee.shanel.ideabucket.factory;

import ee.shanel.ideabucket.model.User;
import org.springframework.mail.MailMessage;

public interface TokenEmailFactory
{
    MailMessage create(User user, String token);
}
