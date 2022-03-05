package ee.shanel.ideabucket.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Repository
@RequiredArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository
{
    private final AuthenticationService authenticationService;

    @Override
    public Mono<Void> save(final ServerWebExchange swe, final SecurityContext securityContext)
    {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(final ServerWebExchange serverWebExchange)
    {
        return Mono.just(serverWebExchange)
                .map(ServerWebExchange::getRequest)
                .map(ServerHttpRequest::getHeaders)
                .flatMap(val -> Mono.justOrEmpty(val.getFirst(AUTHORIZATION)))
                .filter(val -> val.startsWith("Bearer "))
                .map(val -> val.substring(7))
                .map(val -> new UsernamePasswordAuthenticationToken(val, val))
                .flatMap(authenticationService::authenticate)
                .map(SecurityContextImpl::new);
    }

}

