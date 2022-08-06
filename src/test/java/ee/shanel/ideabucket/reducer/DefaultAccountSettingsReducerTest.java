package ee.shanel.ideabucket.reducer;


import ee.shanel.ideabucket.model.entity.AccountSettingsEntity;
import ee.shanel.ideabucket.model.settings.EmailFrequency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultAccountSettingsReducerTest
{
    private static final String USER_ID = "ID";

    private static final String USER_ID_2 = "ID2";

    private DefaultAccountSettingsReducer subject;

    @BeforeEach
    void beforeEach()
    {
        subject = new DefaultAccountSettingsReducer();
    }

    @Test
    void itPrefersTheUpdatedData()
    {
        final var existing = AccountSettingsEntity.builder()
                .userId(USER_ID)
                .emailsEnabled(Boolean.TRUE)
                .emailFrequency(EmailFrequency.Weekly)
                .build();

        final var update = AccountSettingsEntity.builder()
                .userId(USER_ID_2)
                .emailsEnabled(Boolean.FALSE)
                .emailFrequency(EmailFrequency.Monthly)
                .build();

        final var expected = AccountSettingsEntity.builder()
                .userId(USER_ID_2)
                .emailsEnabled(Boolean.FALSE)
                .emailFrequency(EmailFrequency.Monthly)
                .build();

        Assertions.assertEquals(expected, subject.apply(existing, update));
    }

    @Test
    void itRetainsTheExistingDataWhenTheUpdatedDataIsNull()
    {
        final var existing = AccountSettingsEntity.builder()
                .userId(USER_ID)
                .emailsEnabled(Boolean.TRUE)
                .emailFrequency(EmailFrequency.Weekly)
                .build();

        final var update = AccountSettingsEntity.builder()
                .build();

        final var expected = AccountSettingsEntity.builder()
                .userId(USER_ID)
                .emailsEnabled(Boolean.TRUE)
                .emailFrequency(EmailFrequency.Weekly)
                .build();

        Assertions.assertEquals(expected, subject.apply(existing, update));
    }

}
