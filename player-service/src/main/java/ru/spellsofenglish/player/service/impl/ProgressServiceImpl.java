package ru.spellsofenglish.player.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spellsofenglish.player.dto.progress.ProgressDto;
import ru.spellsofenglish.player.entity.Player;
import ru.spellsofenglish.player.entity.Progress;
import ru.spellsofenglish.player.mapper.ProgressMapperDto;
import ru.spellsofenglish.player.repository.ProgressRepository;
import ru.spellsofenglish.player.service.ProgressService;

@Service
public class ProgressServiceImpl implements ProgressService {
    private final ProgressMapperDto progressMapperDto;
    private final ProgressRepository progressRepository;

    @Autowired
    public ProgressServiceImpl(ProgressMapperDto progressMapperDto, ProgressRepository progressRepository) {
        this.progressMapperDto = progressMapperDto;
        this.progressRepository = progressRepository;
    }

    @Override
    public ProgressDto getPlayerProgress(Player player) {
        return progressMapperDto.apply(player.getProgress());
    }

    @Override
    @Transactional
    public void updateProgress(ProgressDto progressDto, Progress oldProgress) {
        oldProgress.setGameLevel(progressDto.gameLevel());
        oldProgress.setTotalPoints(oldProgress.getTotalPoints() + progressDto.totalPoint());
        oldProgress.setProgress((progressDto.gameLevel() * 100) / 48.0);
        progressRepository.save(oldProgress);

    }

    @Override
    @Transactional
    public Progress createProgress() {
        var progress = new Progress();
        progress.setGameLevel(0);
        progress.setTotalPoints(0);
        progress.setProgress(0.0);
        return progressRepository.save(progress);
    }
}