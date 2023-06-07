package ru.spellsofenglish.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spellsofenglish.player.entity.Settings;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, UUID> {
    @Query(value = "SELECT settings.id, game_language, has_audio FROM settings INNER JOIN player " +
            "ON player.settings_id = settings.id AND player.username = :username", nativeQuery = true)
    Optional<Settings> findByUsername(@Param("username") String username);
}
