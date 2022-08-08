package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.model.Idea;
import ee.shanel.ideabucket.model.User;
import org.springframework.lang.Nullable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IdeaService
{
    Mono<Idea> put(Idea idea, User user);

    Flux<Idea> findIdeas(User user, @Nullable String category);

    Mono<Void> delete(String id);
}
