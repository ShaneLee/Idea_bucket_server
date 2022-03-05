package ee.shanel.ideabucket.security;

import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public interface AuthenticationService
{
    Mono<Authentication> authenticate(String token);

    Mono<Authentication> authenticate(Authentication authentication);
}
