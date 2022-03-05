package ee.shanel.ideabucket.converter;

import ee.shanel.ideabucket.model.Idea;
import ee.shanel.ideabucket.model.entity.IdeaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class IdeaEntityToIdeaConverter implements Converter<IdeaEntity, Idea>
{
    @Override
    public Idea convert(final IdeaEntity source)
    {
        return new Idea(
                source.getId(),
                source.getUserId(),
                source.getIdea(),
                source.getCategory(),
                OffsetDateTime.parse(source.getTimeSubmitted())
        );
    }
}
