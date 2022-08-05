package ee.shanel.ideabucket.factory;

import ee.shanel.ideabucket.model.Token;
import ee.shanel.ideabucket.model.User;
import ee.shanel.ideabucket.security.TokenSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DefaultTokenFactory implements TokenFactory
{
    private final Clock clock;

    private final TokenSecurityService tokenSecurityService;

    @Override
    public Token create(final User id)
    {
        return new Token(
                createToken(id),
                id.getId(),
                id.getEmail(),
                Date.from(clock.instant())
        );
    }

    private String createToken(final User user)
    {
        return tokenSecurityService.generate(
                Map.of(
                        "name", user.getName(),
                        "role", user.getRole().getUserRole()
                )
        );
    }
}
