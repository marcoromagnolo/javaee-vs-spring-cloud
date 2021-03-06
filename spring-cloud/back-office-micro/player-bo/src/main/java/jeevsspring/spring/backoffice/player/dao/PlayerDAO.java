package jeevsspring.spring.backoffice.player.dao;

import jeevsspring.spring.backoffice.player.entity.PlayerEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Marco Romagnolo
 */
public interface PlayerDAO extends CrudRepository<PlayerEntity, Long> {

    PlayerEntity getByUsernameAndPassword(String username, String password);

    PlayerEntity findOne(Long id);

    PlayerEntity save(PlayerEntity entity);
}
