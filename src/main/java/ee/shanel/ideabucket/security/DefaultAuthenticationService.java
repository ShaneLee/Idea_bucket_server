package ee.shanel.ideabucket.security;

import ee.shanel.ideabucket.model.User;
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

    private final TokenSecurityService tokenSecurityService;

    @Override
    public Mono<Authentication> authenticate(final String token)
    {
        return Mono.just(token)
                .filter(tokenSecurityService::validate)
                .filterWhen(tokenService::existsByToken)
                .flatMap(userService::findUser)
                .map(DefaultAuthenticationService::createAuthentication)
                .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid auth token for user: " + token)));
    }

    @Override
    public Mono<Authentication> authenticate(final Authentication authentication)
    {
        return Mono.just(authentication.getName())
                .filter(tokenSecurityService::validate)
                .filterWhen(tokenService::existsByToken)
                .flatMap(userService::findUser)
                .map(DefaultAuthenticationService::createAuthentication);
    }

    private static Authentication createAuthentication(final User user)
    {
        final Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getId(),
                null,
                AuthorityUtils.createAuthorityList(user.getRole().toString()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
