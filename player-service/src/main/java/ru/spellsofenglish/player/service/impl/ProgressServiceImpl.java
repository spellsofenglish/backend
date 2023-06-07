package ru.spellsofenglish.player.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spellsofenglish.player.dto.progress.ProgressDto;
import ru.spellsofenglish.player.entity.Progress;
import ru.spellsofenglish.player.mapper.ProgressMapperDto;
import ru.spellsofenglish.player.repository.ProgressRepository;
import ru.spellsofenglish.player.service.ProgressService;

@Service
public class ProgressServiceImpl implements ProgressService {
    private final ProgressRepository progressRepository;
    private final ProgressMapperDto progressMapperDto;

    @Autowired
    public ProgressServiceImpl(ProgressRepository progressRepository, ProgressMapperDto progressMapperDto) {
        this.progressRepository = progressRepository;
        this.progressMapperDto = progressMapperDto;
    }

    @Override
    public ProgressDto getPlayerProgress(String username) {
        return progressRepository.findByUsername(username)
                .map(progressMapperDto)
                .orElseThrow(()-> new IllegalArgumentException("User not found"));
    }

    @Override
    public Progress updateProgress(ProgressDto progressDto) {
        var progress = Progress.builder()
                .progress(progressDto.progress())
                .gameLevel(progressDto.gameLevel())
                .build();
        return progressRepository.save(progress);
    }
}
