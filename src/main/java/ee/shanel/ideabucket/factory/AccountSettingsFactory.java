package ee.shanel.ideabucket.factory;

import ee.shanel.ideabucket.model.settings.AccountSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountSettingsFactory
{
    private static final AccountSettings DEFAULT = new AccountSettings(
           null,
           false
    );

    public AccountSettings createDefaultAccountSettings(final String userId)
    {
        return DEFAULT.toBuilder()
                .userId(userId)
                .build();
    }
}
