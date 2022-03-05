package ee.shanel.ideabucket.converter;

import ee.shanel.ideabucket.model.entity.AccountSettingsEntity;
import ee.shanel.ideabucket.model.settings.AccountSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountSettingsEntityToAccountSettingConverter implements Converter<AccountSettingsEntity, AccountSettings>
{
    @Override
    public AccountSettings convert(final AccountSettingsEntity source)
    {
        return new AccountSettings(
                source.getUserId(),
                source.getEmailsEnabled()
        );
    }
}
