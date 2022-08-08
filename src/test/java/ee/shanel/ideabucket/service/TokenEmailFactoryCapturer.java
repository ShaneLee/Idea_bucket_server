package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.factory.TokenEmailFactory;
import ee.shanel.ideabucket.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailMessage;

import java.util.Map;

@RequiredArgsConstructor
public class TokenEmailFactoryCapturer implements TokenEmailFactory
{
    private final Map<String, String> tokensByEmail;

    private final TokenEmailFactory emailFactory;


    @Override
    public MailMessage create(final User user, final String token)
    {
        tokensByEmail.put(user.getEmail(), token);
        tokensByEmail.put(user.getId(), token);
        return emailFactory.create(user, token);
    }

    public String getTokenFromEmail(final String email)
    {
        return tokensByEmail.get(email);
    }

    public void clear()
    {
        tokensByEmail.clear();
    }
}
