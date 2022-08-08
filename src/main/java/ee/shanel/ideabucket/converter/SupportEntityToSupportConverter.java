package ee.shanel.ideabucket.converter;

import ee.shanel.ideabucket.model.entity.SupportEntity;
import ee.shanel.ideabucket.model.support.Support;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SupportEntityToSupportConverter implements Converter<SupportEntity, Support>
{
    @Override
    public Support convert(final SupportEntity source)
    {
        return new Support(
                source.getId(),
                source.getSubject(),
                source.getUserEmail(),
                source.getUsername(),
                source.getUserId(),
                source.getBody(),
                source.getCategory(),
                source.getTimeReceived()
        );
    }
}
