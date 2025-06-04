package skala.skoro.domain.auth.repository;

import org.springframework.data.repository.CrudRepository;

public interface AccessTokenRepository extends CrudRepository<AccessTokenEntry, String> {
}
