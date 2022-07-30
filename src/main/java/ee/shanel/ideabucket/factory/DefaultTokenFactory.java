package ee.shanel.ideabucket.factory;

import ee.shanel.ideabucket.model.Token;
import ee.shanel.ideabucket.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.Clock;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class DefaultTokenFactory implements TokenFactory
{
    private static final SecureRandom RANDOM = new SecureRandom();

    private static final int TOKEN_BYTE_SIZE = 16;

    private final Clock clock;

    @Override
    public Token create(final User id)
    {
        return new Token(
                create(),
                id.getId(),
                id.getEmail(),
                Date.from(clock.instant())
        );
    }

    private static String create()
    {
        final var bytes = new byte[TOKEN_BYTE_SIZE];
        RANDOM.nextBytes(bytes);
        return String.valueOf(Hex.encode(bytes));
    }
}
