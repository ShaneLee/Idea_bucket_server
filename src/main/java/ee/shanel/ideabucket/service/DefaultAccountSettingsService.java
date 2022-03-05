package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.factory.AccountSettingsFactory;
import ee.shanel.ideabucket.model.User;
import ee.shanel.ideabucket.model.entity.AccountSettingsEntity;
import ee.shanel.ideabucket.model.settings.AccountSettings;
import ee.shanel.ideabucket.repository.AccountSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DefaultAccountSettingsService implements AccountSettingsService
{
    private final AccountSettingsFactory accountSettingsFactory;

    private final AccountSettingsRepository accountSettingsRepository;

    private final ConversionService conversionService;

    @Override
    public Mono<AccountSettings> put(final AccountSettings accountSettings, final User user)
    {
        return Mono.just(user.getId())
                .map(accountSettings::withUserId)
                .flatMap(this::put);
    }

    @Override
    public Mono<AccountSettings> findAccountSettings(final User user)
    {
        return Mono.just(user.getId())
                .flatMap(accountSettingsRepository::findById)
                .mapNotNull(val -> conversionService.convert(val, AccountSettings.class));
    }

    @Override
    public Mono<AccountSettings> saveDefaultSettings(final User user)
    {
        return Mono.just(user.getId())
                .map(accountSettingsFactory::createDefaultAccountSettings)
                .flatMap(this::put);
    }

    private Mono<AccountSettings> put(final AccountSettings settings)
    {
        return Mono.just(settings)
                .mapNotNull(val -> conversionService.convert(val, AccountSettingsEntity.class))
                .flatMap(accountSettingsRepository::save)
                .mapNotNull(val -> conversionService.convert(val, AccountSettings.class));
    }
}
