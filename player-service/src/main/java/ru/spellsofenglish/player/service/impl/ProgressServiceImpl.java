package ru.spellsofenglish.player.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spellsofenglish.player.dto.progress.ProgressDto;
import ru.spellsofenglish.player.entity.Progress;
import ru.spellsofenglish.player.exception.InvalidDataException;
import ru.spellsofenglish.player.repository.ProgressRepository;
import ru.spellsofenglish.player.service.ProgressService;

@Service
public class ProgressServiceImpl implements ProgressService {
    private final ProgressRepository progressRepository;

    @Autowired
    public ProgressServiceImpl(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    @Override
    @Transactional
    public Progress updateProgress(ProgressDto progressDto, Progress oldProgress) {
        var updateTotalPoint = oldProgress.getTotalPoints() + progressDto.totalPoints();

        if (updateTotalPoint < 0)
            throw new InvalidDataException("The resulting total point cannot be negative",
                    "Value  "+ progressDto.totalPoints() + " not valid");

        oldProgress.setGameLevel(progressDto.gameLevel());
        oldProgress.setTotalPoints(updateTotalPoint);
        oldProgress.setProgress((progressDto.gameLevel() * 100) / 48.0);
        return progressRepository.save(oldProgress);

    }
    @Override
    @Transactional
    public Progress createProgress() {
        var progress = new Progress();
        progress.setGameLevel(0);
        progress.setTotalPoints(40);
        progress.setProgress(0.0);
        return progressRepository.save(progress);
    }
}