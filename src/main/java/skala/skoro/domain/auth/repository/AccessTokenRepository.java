package skala.skoro.domain.auth.repository;

import org.springframework.data.repository.CrudRepository;
import skala.skoro.domain.auth.entity.AccessTokenEntry;

public interface AccessTokenRepository extends CrudRepository<AccessTokenEntry, String> {
}
