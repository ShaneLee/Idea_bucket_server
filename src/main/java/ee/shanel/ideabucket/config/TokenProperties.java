package ee.shanel.ideabucket.config;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.core.io.Resource;

import java.time.Duration;

@ConfigurationProperties(prefix = "token")
@ConstructorBinding
@Getter
@Builder(toBuilder = true)
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class TokenProperties
{
    private final Duration expireAfter;
    private final Resource publicKey;
    private final Resource privateKey;
    private final String algorithm;
    private final String issuer;
}
