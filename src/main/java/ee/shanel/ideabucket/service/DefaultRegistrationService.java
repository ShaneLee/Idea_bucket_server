package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.factory.TokenEmailFactory;
import ee.shanel.ideabucket.model.Registration;
import ee.shanel.ideabucket.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultRegistrationService implements RegistrationService
{
    private final AccountSettingsService accountSettingsService;

    private final TokenEmailFactory registrationConfirmationEmailFactory;

    private final SenderService senderService;

    private final UserService userService;

    @Override
    public Mono<User> register(final Registration registration)
    {
        return userService.register(registration)
                .doOnError(val -> LOG.debug("User registration error: ", val))
                .flatMap(val -> Mono.just(val)
                        .flatMap(accountSettingsService::saveDefaultSettings)
                        .thenReturn(val))
                .flatMap(this::sendConfirmation);
    }

    private Mono<User> sendConfirmation(final User user)
    {
        return Mono.just(user)
                .map(val -> registrationConfirmationEmailFactory.create(val, user.getToken()))
                .flatMap(val -> senderService.send(val)
                        .thenReturn(user));
    }
}
