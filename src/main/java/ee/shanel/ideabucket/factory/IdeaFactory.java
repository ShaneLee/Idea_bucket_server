package ee.shanel.ideabucket.factory;

import ee.shanel.ideabucket.generator.IdGenerator;
import ee.shanel.ideabucket.model.Idea;
import ee.shanel.ideabucket.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class IdeaFactory
{
    private final Clock clock;

    private final IdGenerator idGenerator;

    public Idea create(final Idea idea, final User user)
    {
        return new Idea(
                idGenerator.generate(),
                user.getId(),
                idea.getIdea(),
                idea.getCategory(),
                OffsetDateTime.now(clock)
        );
    }
}
