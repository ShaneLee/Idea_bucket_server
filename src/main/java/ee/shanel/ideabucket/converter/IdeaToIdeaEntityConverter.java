package ee.shanel.ideabucket.converter;

import ee.shanel.ideabucket.model.Idea;
import ee.shanel.ideabucket.model.entity.IdeaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdeaToIdeaEntityConverter implements Converter<Idea, IdeaEntity>
{
    @Override
    public IdeaEntity convert(final Idea source)
    {
        return new IdeaEntity(
                source.getId(),
                source.getUserId(),
                source.getIdea(),
                source.getCategory(),
                source.getTimeSubmitted().toString()
        );
    }
}
