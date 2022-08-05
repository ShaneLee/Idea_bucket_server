package ee.shanel.ideabucket.factory;

import ee.shanel.ideabucket.model.User;
import ee.shanel.ideabucket.model.authentication.IdeaBucketRole;
import ee.shanel.ideabucket.security.TokenSecurityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
class DefaultTokenFactoryTest
{
    private static final Clock CLOCK = Clock.fixed(Instant.parse("2020-06-04T14:30:30.000Z"), ZoneId.of("UTC"));

    private static final String TOKEN = "TOKEN";

    private static final String NAME = "NAME";

    private static final String EMAIL = "test@example.com";

    private static final String USER_ID = "ID";

    private static final IdeaBucketRole ROLE = IdeaBucketRole.standard();

    private static final User USER = createUser();

    @Mock
    private TokenSecurityService mockTokenSecurityService;

    private DefaultTokenFactory subject;

    @BeforeEach
    void setUp()
    {
        subject = new DefaultTokenFactory(
                CLOCK,
                mockTokenSecurityService
        );
    }

    @Test
    void itCreates()
    {
        Mockito.when(mockTokenSecurityService.generate(Mockito.anyMap()))
                .thenReturn(TOKEN);


        final var result = subject.create(USER);

        Assertions.assertAll(() ->
        {
            Assertions.assertEquals(EMAIL, result.getEmail());
            Assertions.assertEquals(USER_ID, result.getUserId());
            Assertions.assertEquals(Date.from(CLOCK.instant()), result.getCreationTime());
            Assertions.assertEquals(TOKEN, result.getToken());
        });
    }

    private static User createUser()
    {
        return User.builder()
                .name(NAME)
                .email(EMAIL)
                .id(USER_ID)
                .role(ROLE)
                .build();
    }
}
