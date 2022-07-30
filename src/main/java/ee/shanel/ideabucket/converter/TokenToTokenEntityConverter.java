package ee.shanel.ideabucket.converter;

import ee.shanel.ideabucket.model.Token;
import ee.shanel.ideabucket.model.entity.TokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenToTokenEntityConverter implements Converter<Token, TokenEntity>
{
    @Override
    public TokenEntity convert(final Token source)
    {
        return new TokenEntity(
                source.getUserId(),
                source.getEmail(),
                source.getToken(),
                source.getCreationTime()
        );
    }
}
