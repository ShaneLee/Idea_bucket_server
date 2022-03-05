package ee.shanel.ideabucket.converter;

import ee.shanel.ideabucket.model.entity.AccountSettingsEntity;
import ee.shanel.ideabucket.model.settings.AccountSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountSettingsToAccountSettingEntityConverter implements Converter<AccountSettings, AccountSettingsEntity>
{
    @Override
    public AccountSettingsEntity convert(final AccountSettings source)
    {
        return new AccountSettingsEntity(
                source.getUserId(),
                source.getEmailsEnabled()
        );
    }
}
