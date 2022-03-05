package ee.shanel.ideabucket.repository;

import ee.shanel.ideabucket.model.entity.AccountSettingsEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountSettingsRepository extends ReactiveCrudRepository<AccountSettingsEntity, String>
{
}

