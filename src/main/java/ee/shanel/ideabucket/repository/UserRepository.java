package ee.shanel.ideabucket.repository;

import ee.shanel.ideabucket.model.User;
import ee.shanel.ideabucket.model.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserEntity, String>
{
    Mono<Boolean> existsByEmail(String emails);

    Mono<User> findByEmail(String email);
}

