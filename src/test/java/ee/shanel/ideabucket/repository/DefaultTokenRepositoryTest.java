package ee.shanel.ideabucket.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.Map;

class DefaultTokenRepositoryTest
{
    private static final String ID = "1";

    private static final String TOKEN = "2";

    @Test
    void itPuts()
    {
        final Map<String, String> map = new HashMap<>();
        final Map<String, String> tokensByEmail = new HashMap<>();
        final var subject = new DefaultTokenRepository(map, tokensByEmail);

        StepVerifier.create(subject.put(ID, TOKEN))
                .assertNext(res -> Assertions.assertEquals(TOKEN, res))
                .verifyComplete();

        Assertions.assertEquals(TOKEN, map.get(TOKEN));
        Assertions.assertEquals(TOKEN, tokensByEmail.get(ID));
    }

    @Test
    void itGets()
    {
        final Map<String, String> map = new HashMap<>();
        final Map<String, String> tokensByEmail = new HashMap<>();
        map.put(TOKEN, TOKEN);

        final var subject = new DefaultTokenRepository(map, tokensByEmail);

        StepVerifier.create(subject.get(TOKEN))
                .assertNext(res -> Assertions.assertEquals(TOKEN, res))
                .verifyComplete();
    }

    @Test
    void itGetsByEmail()
    {
        final Map<String, String> map = new HashMap<>();
        final Map<String, String> tokensByEmail = new HashMap<>();

        final var subject = new DefaultTokenRepository(map, tokensByEmail);

        subject.put(ID, TOKEN).block();

        StepVerifier.create(subject.getByEmail(ID))
                .assertNext(res -> Assertions.assertEquals(TOKEN, res))
                .verifyComplete();
    }

    @Test
    void itDeletes()
    {
        final Map<String, String> map = new HashMap<>();
        final Map<String, String> tokensByEmail = new HashMap<>();
        map.put(TOKEN, TOKEN);

        final var subject = new DefaultTokenRepository(map, tokensByEmail);

        StepVerifier.create(subject.delete(TOKEN))
                .assertNext(res -> Assertions.assertEquals(TOKEN, res))
                .verifyComplete();

        Assertions.assertNull(map.get(TOKEN));
        Assertions.assertNull(tokensByEmail.get(ID));
    }

    @Test
    void itDeletesAll()
    {
        final Map<String, String> map = new HashMap<>();
        final Map<String, String> tokensByEmail = new HashMap<>();
        map.put(TOKEN, TOKEN);

        final var subject = new DefaultTokenRepository(map, tokensByEmail);

        StepVerifier.create(subject.deleteAll())
                .expectNext(Boolean.TRUE)
                .verifyComplete();

        Assertions.assertNull(map.get(TOKEN));
        Assertions.assertNull(tokensByEmail.get(ID));
    }
}
