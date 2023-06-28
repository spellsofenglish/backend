package ru.spellsofenglish.player.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.spellsofenglish.player.entity.Progress;

import java.util.UUID;

@Repository
public interface ProgressRepository extends CrudRepository<Progress, UUID> {
}
