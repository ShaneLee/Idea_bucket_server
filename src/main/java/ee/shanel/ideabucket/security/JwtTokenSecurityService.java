package ee.shanel.ideabucket.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import ee.shanel.ideabucket.config.TokenProperties;
import ee.shanel.ideabucket.model.Token;
import ee.shanel.ideabucket.security.pem.PemUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Clock;
import java.time.Instant;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenSecurityService implements TokenSecurityService
{
    private final Clock clock;

    private final TokenProperties tokenProperties;

    private Algorithm algorithm;

    private JWTVerifier jwtVerifier;

    @PostConstruct
    void postConstruct()
    {
        algorithm = Algorithm.RSA256(getPublicKey(), getPrivateKey());
        jwtVerifier = JWT.require(algorithm)
                .withIssuer(tokenProperties.getIssuer())
                .build();
    }

    @Override
    @SneakyThrows
    public String generate(final Map<String, Object> claims)
    {
        return JWT.create()
                .withPayload(claims)
                .withIssuer(tokenProperties.getIssuer())
                .withExpiresAt(Instant.now(clock).plus(tokenProperties.getExpireAfter()))
                .sign(algorithm);
    }

    @Override
    public Token decode(final String token)
    {
        return null;
    }

    @Override
    public Boolean validate(final String token)
    {
        try
        {
            jwtVerifier.verify(token);
            return Boolean.TRUE;

        } catch (final JWTVerificationException exception)
        {
            LOG.error("Invalid token " + exception.getMessage());
            return Boolean.FALSE;
        }
    }

    @SneakyThrows
    private RSAPublicKey getPublicKey()
    {
        return (RSAPublicKey) PemUtils.readPublicKeyFromFile(
                tokenProperties.getPublicKey().getInputStream(),
                tokenProperties.getAlgorithm()
        );

    }

    @SneakyThrows
    private RSAPrivateKey getPrivateKey()
    {
        return (RSAPrivateKey) PemUtils.readPrivateKeyFromFile(
                tokenProperties.getPrivateKey().getInputStream(),
                tokenProperties.getAlgorithm()
        );
    }
}
