package ru.spellsofenglish.gameservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.spellsofenglish.gameservice.models.Game;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GameRepository extends CrudRepository<Game, UUID> {
    Optional<Game> findByPlayerId(UUID playerId);
    boolean existsByPlayerId(UUID playerId);
}
