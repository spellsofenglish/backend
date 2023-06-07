package ru.spellsofenglish.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spellsofenglish.player.entity.Progress;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface ProgressRepository extends JpaRepository<Progress, UUID> {
    @Query(value = "select progress.id, game_level, progress " +
            "from progress inner join player on progress.id = player.progress_id " +
            "AND player.username = :username", nativeQuery = true)
    Optional<Progress> findByUsername(@Param("username") String username);
}
