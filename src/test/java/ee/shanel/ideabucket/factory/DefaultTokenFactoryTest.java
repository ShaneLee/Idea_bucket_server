package ee.shanel.ideabucket.factory;

import ee.shanel.ideabucket.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultTokenFactoryTest
{
    private static final User USER = createUser();

    private DefaultTokenFactory subject;

    @BeforeEach
    void setUp()
    {
        subject = new DefaultTokenFactory();
    }

    @Test
    void itCreatesAUniqueToken()
    {
        Assertions.assertNotEquals(subject.create(USER), subject.create(USER));
    }

    private static User createUser()
    {
        return User.builder()
                .build();
    }
}
