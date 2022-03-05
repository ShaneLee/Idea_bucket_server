package ee.shanel.ideabucket.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class DefaultTokenRepository implements TokenRepository
{
    private final Map<String, String> tokens;

    private final Map<String, String> tokenByEmail;

    @Override
    public Mono<String> put(final String id, final String token)
    {
        return Mono.justOrEmpty(tokens.put(token, token))
                .then(Mono.justOrEmpty(tokenByEmail.put(id, token)))
                .thenReturn(token);
    }

    @Override
    public Mono<String> get(final String id)
    {
        return Mono.justOrEmpty(tokens.get(id));

//        return Mono.just(id);
    }

    @Override
    public Mono<String> getByEmail(final String email)
    {
        return Mono.justOrEmpty(tokenByEmail.get(email));
    }

    @Override
    public Mono<String> delete(final String id)
    {
        return Mono.justOrEmpty(tokens.remove(id));
    }

    @Override
    public Mono<Boolean> deleteAll()
    {
        return Mono.fromRunnable(tokens::clear)
                .thenReturn(Boolean.TRUE);
    }
}
