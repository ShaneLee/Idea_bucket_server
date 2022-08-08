package ee.shanel.ideabucket.factory;

import ee.shanel.ideabucket.generator.IdGenerator;
import ee.shanel.ideabucket.model.User;
import ee.shanel.ideabucket.model.support.Support;
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
class SupportFactoryTest
{
    private static final Clock CLOCK = Clock.fixed(Instant.parse("2020-06-04T14:30:30.000Z"), ZoneId.of("UTC"));

    private static final Date NOW = Date.from(Instant.now(CLOCK));

    private static final String ID = "ID";

    private static final String USER_ID = "USER_ID";

    private static final String USER_NAME = "USER_NAME";

    private static final String USER_EMAIL = "USER_EMAIL";

    private static final String SUBJECT = "SUBJECT";

    private static final String BODY = "BODY";

    private static final String CATEGORY = "CATEGORY";

    private static final User USER = createUser();

    private static final Support SUPPORT = createSupport();

    @Mock
    private IdGenerator mockIdGenerator;

    private SupportFactory subject;

    @BeforeEach
    void beforeEach()
    {
        subject = new SupportFactory(
                CLOCK,
                mockIdGenerator
        );
    }

    @Test
    void itCreates()
    {
        Mockito.when(mockIdGenerator.generate())
                .thenReturn(ID);

        final var expected = Support.builder()
                .id(ID)
                .subject(SUBJECT)
                .userEmail(USER_EMAIL)
                .username(USER_NAME)
                .userId(USER_ID)
                .body(BODY)
                .category(CATEGORY)
                .timeReceived(NOW)
                .build();


        Assertions.assertEquals(expected, subject.create(SUPPORT, USER));
    }

    private static Support createSupport()
    {
        return new Support(
                ID,
                SUBJECT,
                null,
                null,
                null,
                BODY,
                CATEGORY,
                NOW
        );
    }

    private static User createUser()
    {
        return User.builder()
                .id(USER_ID)
                .email(USER_EMAIL)
                .name(USER_NAME)
                .build();
    }

}
