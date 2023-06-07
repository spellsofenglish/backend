package ru.spellsofenglish.player.service;

import org.springframework.stereotype.Service;
import ru.spellsofenglish.player.dto.player.DataPlayerDto;
import ru.spellsofenglish.player.dto.player.PlayerDto;
import ru.spellsofenglish.player.entity.Progress;
import ru.spellsofenglish.player.entity.Settings;

@Service
public interface PlayerService {
    DataPlayerDto getPlayer(String username);
    void updatePlayer(PlayerDto playerDto, Settings settings, Progress progress);
}
