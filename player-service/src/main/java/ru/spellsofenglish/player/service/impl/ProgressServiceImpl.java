package ru.spellsofenglish.player.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spellsofenglish.player.dto.progress.ProgressDto;
import ru.spellsofenglish.player.entity.Player;
import ru.spellsofenglish.player.entity.Progress;
import ru.spellsofenglish.player.exception.InvalidDataException;
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
    public void updateProgress(ProgressDto progressDto, Progress oldProgress) throws InvalidDataException {
        progressRepository.save(oldProgress
                .setGameLevel(progressDto.gameLevel())
                .setTotalPoints(oldProgress.getTotalPoints() + progressDto.totalPoint())
                .setProgress((progressDto.gameLevel() * 100) / 48.0));

    }

    @Override
    @Transactional
    public Progress createProgress() {
        return progressRepository.save(new Progress().setProgress(0d).setTotalPoints(50).setGameLevel(0));
    }
}