package ru.spellsofenglish.player.service;

import org.springframework.stereotype.Service;
import ru.spellsofenglish.player.dto.progress.ProgressDto;
import ru.spellsofenglish.player.entity.Player;
import ru.spellsofenglish.player.entity.Progress;

@Service
public interface ProgressService {
    ProgressDto getPlayerProgress(Player player);
    void updateProgress(ProgressDto progressDto, Progress oldProgress);
    Progress createProgress();

}
