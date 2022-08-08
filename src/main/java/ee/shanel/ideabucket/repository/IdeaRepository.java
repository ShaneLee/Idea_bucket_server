package ee.shanel.ideabucket.repository;

import ee.shanel.ideabucket.model.entity.IdeaEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface IdeaRepository extends ReactiveCrudRepository<IdeaEntity, String>
{
    Flux<IdeaEntity> findAllByUserId(String userId);

    Flux<IdeaEntity> findAllByUserIdAndCategory(String userId, String category);
}

