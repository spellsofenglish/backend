package ru.spellsofenglish.player.service.progress;

import org.springframework.stereotype.Service;
import ru.spellsofenglish.player.entity.Progress;

@Service
public interface ProgressService {
    Progress defaultProgress();
}
