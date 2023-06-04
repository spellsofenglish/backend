package ru.spellsofenglish.player.service.progress;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spellsofenglish.player.entity.Progress;
import ru.spellsofenglish.player.repository.ProgressRepository;

@Service
public class ProgressServiceImpl implements ProgressService {
    private final ProgressRepository progressRepository;

    @Autowired
    public ProgressServiceImpl(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    @Override
    @Transactional
    public Progress defaultProgress() {
        return progressRepository.save(
                new Progress()
                        .setProgress(0L)
                        .setGameLevel(1L));
    }
}
