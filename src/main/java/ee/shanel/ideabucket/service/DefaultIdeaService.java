package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.factory.IdeaFactory;
import ee.shanel.ideabucket.model.Idea;
import ee.shanel.ideabucket.model.User;
import ee.shanel.ideabucket.model.entity.IdeaEntity;
import ee.shanel.ideabucket.repository.IdeaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class DefaultIdeaService implements IdeaService
{
    private final ConversionService conversionService;

    private final IdeaFactory ideaFactory;

    private final IdeaRepository ideaRepository;

    @Override
    public Mono<Idea> put(final Idea idea, final User user)
    {
        return Mono.just(idea)
                .map(val -> ideaFactory.create(val, user))
                .mapNotNull(val -> conversionService.convert(val, IdeaEntity.class))
                .flatMap(ideaRepository::save)
                .thenReturn(idea);
    }

    @Override
    public Flux<Idea> findIdeas(final User user, final String category)
    {
        return Mono.just(user)
                .flatMapMany(val -> category == null
                        ? ideaRepository.findAllByUserId(user.getId())
                        : ideaRepository.findAllByUserIdAndCategory(user.getId(), category))
                .mapNotNull(val -> conversionService.convert(val, Idea.class));
    }

    @Override
    public Mono<Void> delete(final String id)
    {
        return ideaRepository.deleteById(id);
    }
}
