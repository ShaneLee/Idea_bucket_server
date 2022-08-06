package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.exception.UserAlreadyExistsException;
import ee.shanel.ideabucket.factory.UserFactory;
import ee.shanel.ideabucket.model.Registration;
import ee.shanel.ideabucket.model.User;
import ee.shanel.ideabucket.model.entity.UserEntity;
import ee.shanel.ideabucket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class DefaultUserService implements UserService
{
    private final ConversionService conversionService;

    private final TokenService tokenService;

    private final UserFactory userFactory;

    private final UserRepository userRepository;

    @Override
    public Mono<User> put(final User user)
    {
        return Mono.just(user)
                .mapNotNull(val -> conversionService.convert(val, UserEntity.class))
                .flatMap(userRepository::save)
                .thenReturn(user);
    }

    @Override
    public Mono<Boolean> exists(final String email)
    {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Mono<User> findUser(final String token)
    {
        return userRepository.findAll()
                .takeLast(1)
                .next()
                .mapNotNull(val -> conversionService.convert(val, User.class));
    }

    @Override
    public Mono<User> findUserByEmail(final String email)
    {
        return userRepository.findByEmail(email);
    }

    @Override
    public Mono<User> register(final Registration registration)
    {
        return Mono.just(registration)
            .filterWhen(val -> exists(val.getEmail()).map(res -> !res))
            .map(userFactory::create)
            .flatMap(val -> tokenService.create(val).map(val::withToken))
            .mapNotNull(val -> conversionService.convert(val, UserEntity.class))
            .flatMap(userRepository::save)
            .mapNotNull(val -> conversionService.convert(val, User.class))
            .switchIfEmpty(Mono.error(new UserAlreadyExistsException(registration.getEmail())));
    }

    @Override
    public Mono<Boolean> revokeToken(final String token)
    {
        return null;
    }
}
