package ee.shanel.ideabucket.factory;

import ee.shanel.ideabucket.model.settings.AccountSettings;
import ee.shanel.ideabucket.model.settings.EmailFrequency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountSettingsFactory
{
    private static final AccountSettings DEFAULT = new AccountSettings(
            null,
            false,
            EmailFrequency.Monthly
    );

    public AccountSettings createDefaultAccountSettings(final String userId)
    {
        return DEFAULT.toBuilder()
                .userId(userId)
                .build();
    }
}
