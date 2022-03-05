package ee.shanel.ideabucket.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ee.shanel.ideabucket.model.authentication.IdeaBucketRole.ROLE_PREFIX;

@Component
@RequiredArgsConstructor
public class DefaultAuthenticationManager implements ReactiveAuthenticationManager
{
    @Override
    public Mono<Authentication> authenticate(final Authentication tokenAuthentication)
    {
        return Mono.just(buildAuthentication(Collections.singletonList("BUCKET_SUBSCRIBED"),
                tokenAuthentication.getCredentials().toString()));
    }

    private Authentication buildAuthentication(final List<String> roles, final String token)
    {
        final List<GrantedAuthority> authorities = roles.stream()
                .map(ROLE_PREFIX::concat)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(token, null, authorities);
    }
}

