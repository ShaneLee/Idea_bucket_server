package ee.shanel.ideabucket.factory;

import ee.shanel.ideabucket.generator.IdGenerator;
import ee.shanel.ideabucket.model.Registration;
import ee.shanel.ideabucket.model.User;
import ee.shanel.ideabucket.model.authentication.IdeaBucketRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFactory
{
    private final IdGenerator idGenerator;

    public User create(final Registration registration)
    {
       return User.builder()
               .id(idGenerator.generate())
               .name(registration.getName())
               .email(registration.getEmail())
               .role(IdeaBucketRole.standard())
               .build();
    }
}
