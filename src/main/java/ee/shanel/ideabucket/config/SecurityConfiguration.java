package ee.shanel.ideabucket.config;

import ee.shanel.ideabucket.model.authentication.IdeaBucketRole;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import reactor.core.publisher.Mono;

@Configuration
public class SecurityConfiguration
{
    @Bean
    SecurityWebFilterChain springSecurityFilterChain(
            final ServerHttpSecurity http,
            final ReactiveAuthenticationManager authenticationManager,
            final ServerSecurityContextRepository securityContextRepository)
    {
        return http
                .exceptionHandling()
                .accessDeniedHandler((exchange, exception) -> Mono.fromRunnable(() -> exchange.getResponse()
                        .setStatusCode(HttpStatus.FORBIDDEN)))
                .authenticationEntryPoint((exchange, exception) -> Mono.fromRunnable(() -> exchange.getResponse()
                        .setStatusCode(HttpStatus.FORBIDDEN)))
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .pathMatchers("/rest/*/login/**", "/rest/*/register*", "/rest/*/mailingList*").permitAll()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .anyExchange().authenticated()
                .and()
                .build();
    }

    @Bean
    @ConfigurationProperties(prefix = "roles")
    public RoleProperties roleProperties()
    {
        return new RoleProperties();
    }

    @Data
    public static class RoleProperties
    {
        private IdeaBucketRole standard = IdeaBucketRole.standard();
    }
}

