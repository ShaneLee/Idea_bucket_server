package ee.shanel.ideabucket.security;

import ee.shanel.ideabucket.model.authentication.IdeaBucketRole;
import ee.shanel.ideabucket.service.TokenService;
import ee.shanel.ideabucket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DefaultAuthenticationService implements AuthenticationService
{
    private final UserService userService;

    private final TokenService tokenService;

    @Override
    public Mono<Authentication> authenticate(final String token)
    {
        return Mono.just(token)
                .filterWhen(tokenService::existsByToken)
                .flatMap(userService::findUser)
                .map(val -> createAuthentication(token))
                .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid auth token for user: " + token)));
    }

    @Override
    public Mono<Authentication> authenticate(final Authentication authentication)
    {
        return Mono.just(authentication.getName())
                .filterWhen(tokenService::existsByToken)
                .flatMap(userService::findUser)
                .map(val -> createAuthentication(authentication.getName()));
    }

    private static Authentication createAuthentication(final String id)
    {
        final Authentication authentication = new UsernamePasswordAuthenticationToken(
                id,
                null,
                AuthorityUtils.createAuthorityList(IdeaBucketRole.standard().toString()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
