package ru.spellsofenglish.player.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.spellsofenglish.player.entity.Player;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface PlayerRepository extends CrudRepository<Player,UUID> {

    Optional<Player> findPlayerById(UUID id);
    Boolean existsByUsername(String username);

}
