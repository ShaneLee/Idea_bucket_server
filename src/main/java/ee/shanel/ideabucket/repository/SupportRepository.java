package ee.shanel.ideabucket.repository;

import ee.shanel.ideabucket.model.entity.SupportEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportRepository extends ReactiveCrudRepository<SupportEntity, String>
{
}

