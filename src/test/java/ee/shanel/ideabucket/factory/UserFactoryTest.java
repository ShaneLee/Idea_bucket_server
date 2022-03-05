package ee.shanel.ideabucket.factory;

import ee.shanel.ideabucket.generator.IdGenerator;
import ee.shanel.ideabucket.model.Registration;
import ee.shanel.ideabucket.model.User;
import ee.shanel.ideabucket.model.authentication.IdeaBucketRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserFactoryTest
{
    private static final String ID = "ID";

    private static final String NAME = "NAME";

    private static final String EMAIL = "EMAIL";

    @Mock
    private IdGenerator mockIdGenerator;

    private UserFactory subject;

    @BeforeEach
    void setUp()
    {
        subject = new UserFactory(
                mockIdGenerator
        );
    }

    @Test
    void itCreatesAStandardUser()
    {
        Mockito.when(mockIdGenerator.generate())
                .thenReturn(ID);

        final var registration = Registration.builder()
                .name(NAME)
                .email(EMAIL)
                .build();
        final var expected = User.builder()
                .name(NAME)
                .id(ID)
                .email(EMAIL)
                .role(IdeaBucketRole.standard())
                .build();

        Assertions.assertEquals(expected, subject.create(registration));
    }
}
