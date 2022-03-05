package ee.shanel.ideabucket.service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Map;

@RequiredArgsConstructor
public class SenderServiceCapturer implements SenderService
{
    private final Map<String, String> tokensByEmail;

    private final SenderService senderService;


    @Override
    public Mono<Void> send(final String userId, final String token)
    {
        tokensByEmail.put(userId, token);
        return senderService.send(userId, token);
    }

    public String getToken(final String email)
    {
        return tokensByEmail.get(email);
    }

    public void clear()
    {
        tokensByEmail.clear();
    }
}
