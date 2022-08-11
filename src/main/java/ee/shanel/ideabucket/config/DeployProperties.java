package ee.shanel.ideabucket.config;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "deploy")
@ConstructorBinding
@Getter
@Builder(toBuilder = true)
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class DeployProperties
{
    private final String host;
}
