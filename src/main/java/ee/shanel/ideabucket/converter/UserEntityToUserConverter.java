package ee.shanel.ideabucket.converter;

import ee.shanel.ideabucket.model.User;
import ee.shanel.ideabucket.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEntityToUserConverter implements Converter<UserEntity, User>
{
    @Override
    public User convert(final UserEntity source)
    {
        return new User(
                source.getId(),
                source.getToken(),
                source.getName(),
                source.getEmail(),
                source.getRole()
        );
    }
}
