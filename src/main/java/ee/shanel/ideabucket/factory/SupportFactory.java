package ee.shanel.ideabucket.factory;

import ee.shanel.ideabucket.generator.IdGenerator;
import ee.shanel.ideabucket.model.User;
import ee.shanel.ideabucket.model.support.Support;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Clock;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class SupportFactory
{
    private final Clock clock;

    private final IdGenerator idGenerator;

    public Support create(final Support support, final User user)
    {
        return support.toBuilder()
                .id(idGenerator.generate())
                .username(user.getName())
                .userEmail(user.getEmail())
                .userId(user.getId())
                .timeReceived(Date.from(Instant.now(clock)))
                .build();
    }
}
