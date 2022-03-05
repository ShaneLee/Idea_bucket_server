package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.model.User;
import ee.shanel.ideabucket.model.settings.AccountSettings;
import reactor.core.publisher.Mono;

public interface AccountSettingsService
{
    Mono<AccountSettings> put(AccountSettings accountSettings, User user);

    Mono<AccountSettings> findAccountSettings(User user);

    Mono<AccountSettings> saveDefaultSettings(User user);
}
