package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.factory.RegistrationConfirmationEmailFactory;
import ee.shanel.ideabucket.model.Registration;
import ee.shanel.ideabucket.model.User;
import ee.shanel.ideabucket.model.settings.AccountSettings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class DefaultRegistrationServiceTest
{
    private static final String ID = "ID";

    private static final String EMAIL = "EMAIL";

    private static final String TOKEN = "TOKEN";

    private static final AccountSettings SETTINGS = createAccountSettings();

    private static final Registration REGISTRATION = createRegistration();

    private static final User USER = createUser();

    private static final MailMessage MESSAGE = new SimpleMailMessage();

    @Mock
    private AccountSettingsService mockAccountSettingsService;

    @Mock
    private RegistrationConfirmationEmailFactory mockFactory;

    @Mock
    private SenderService mockSenderService;

    @Mock
    private UserService mockUserService;

    private DefaultRegistrationService subject;

    @BeforeEach
    void setUp()
    {
        subject = new DefaultRegistrationService(
                mockAccountSettingsService,
                mockFactory,
                mockSenderService,
                mockUserService
        );
    }

    @Test
    void itRegisters()
    {
        Mockito.when(mockUserService.register(Mockito.any()))
                .thenReturn(Mono.just(USER));
        Mockito.when(mockAccountSettingsService.saveDefaultSettings(Mockito.any()))
                .thenReturn(Mono.just(SETTINGS));
        Mockito.when(mockFactory.create(Mockito.any(), Mockito.anyString()))
                .thenReturn(MESSAGE);
        Mockito.when(mockSenderService.send(Mockito.any()))
                .thenReturn(Mono.empty());

        StepVerifier.create(subject.register(REGISTRATION))
                .assertNext(res -> Assertions.assertEquals(USER, res))
                .verifyComplete();

        Mockito.verify(mockUserService).register(REGISTRATION);
        Mockito.verify(mockAccountSettingsService).saveDefaultSettings(USER);
        Mockito.verify(mockFactory).create(USER, USER.getToken());
        Mockito.verify(mockSenderService).send(MESSAGE);
    }

    private static User createUser()
    {
        return User.builder()
                .id(ID)
                .email(EMAIL)
                .token(TOKEN)
                .build();
    }

    private static Registration createRegistration()
    {
        return Registration.builder()
                .email(EMAIL)
                .build();
    }

    private static AccountSettings createAccountSettings()
    {
        return AccountSettings.builder()
                .build();
    }

}
