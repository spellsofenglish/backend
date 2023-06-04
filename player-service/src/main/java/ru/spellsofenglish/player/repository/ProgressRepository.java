package ru.spellsofenglish.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spellsofenglish.player.entity.Progress;

import java.util.UUID;
@Repository
public interface ProgressRepository extends JpaRepository<Progress, UUID> {
}
