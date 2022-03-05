package ee.shanel.ideabucket.factory;

import ee.shanel.ideabucket.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class DefaultTokenFactory implements TokenFactory
{
    private static final SecureRandom RANDOM = new SecureRandom();

    private static final int TOKEN_BYTE_SIZE = 16;

    @Override
    public String create(final User id)
    {
        final var bytes = new byte[TOKEN_BYTE_SIZE];
        RANDOM.nextBytes(bytes);
        return String.valueOf(Hex.encode(bytes));
    }
}
