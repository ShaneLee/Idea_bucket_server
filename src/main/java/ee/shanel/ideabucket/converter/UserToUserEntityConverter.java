package ee.shanel.ideabucket.converter;

import ee.shanel.ideabucket.model.User;
import ee.shanel.ideabucket.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserToUserEntityConverter implements Converter<User, UserEntity>
{
    @Override
    public UserEntity convert(final User source)
    {
        return new UserEntity(
                source.getId(),
                source.getToken(),
                source.getName(),
                source.getEmail(),
                source.getRole()
        );
    }
}
