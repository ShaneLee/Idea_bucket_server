package ee.shanel.ideabucket.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import ee.shanel.ideabucket.config.TokenProperties;
import ee.shanel.ideabucket.model.Token;
import ee.shanel.ideabucket.security.pem.PemUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;


import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Clock;
import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtTokenSecurityService implements TokenSecurityService
{
    private final Clock clock;

    private final TokenProperties tokenProperties;

    @Override
    @SneakyThrows
    public String generate(final Map<String, Object> claims)
    {
        return JWT.create()
                .withPayload(claims)
                .withExpiresAt(Instant.now(clock).plus(tokenProperties.getExpireAfter()))
                .sign(Algorithm.RSA256(
                        (RSAPublicKey) PemUtils.readPublicKeyFromFile(
                                tokenProperties.getPublicKeyPath(),
                                tokenProperties.getAlgorithm()
                        ),
                        (RSAPrivateKey) PemUtils.readPrivateKeyFromFile(
                               tokenProperties.getPrivateKeyPath(),
                                tokenProperties.getAlgorithm()
                        )
                ));
    }

    @Override
    public Token decode(final String token)
    {
        return null;
    }

    @Override
    public Boolean validate(final String token)
    {
        return null;
    }
}
