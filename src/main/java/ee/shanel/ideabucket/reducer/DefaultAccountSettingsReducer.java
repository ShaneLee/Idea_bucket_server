package ee.shanel.ideabucket.reducer;

import ee.shanel.ideabucket.model.entity.AccountSettingsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultAccountSettingsReducer implements AccountSettingsReducer
{
    @Override
    public AccountSettingsEntity apply(final AccountSettingsEntity existing, final AccountSettingsEntity update)
    {
        return new AccountSettingsEntity(
                update.getUserId() == null ? existing.getUserId() : update.getUserId(),
                update.getEmailsEnabled() == null ? existing.getEmailsEnabled() : update.getEmailsEnabled(),
                update.getEmailFrequency() == null ? existing.getEmailFrequency() : update.getEmailFrequency()
        );

    }
}
