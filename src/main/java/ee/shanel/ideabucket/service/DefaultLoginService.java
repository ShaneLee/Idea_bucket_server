package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.model.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DefaultLoginService implements LoginService
{
    private final UserService userService;

    private final SenderService senderService;

    private final TokenService tokenService;

    @Override
    public Mono<Boolean> login(final LoginRequest request)
    {
        return Mono.just(request)
                .map(LoginRequest::getEmail)
                .flatMap(userService::findUserByEmail)
                .flatMap(val -> tokenService.getByEmail(val.getEmail())
                        .switchIfEmpty(Mono.defer(() -> tokenService.create(val))))
                .flatMap(val -> senderService.send(request.getEmail(), val).thenReturn(Boolean.TRUE))
                .defaultIfEmpty(Boolean.FALSE);
    }
}
