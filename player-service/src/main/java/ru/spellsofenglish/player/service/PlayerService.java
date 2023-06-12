package ru.spellsofenglish.player.service;

import org.springframework.stereotype.Service;
import ru.spellsofenglish.player.dto.player.PlayerDto;
import ru.spellsofenglish.player.entity.Player;
import ru.spellsofenglish.player.entity.Progress;

import java.util.UUID;

@Service
public interface PlayerService {
    PlayerDto getPlayer(UUID id);

    Player findPlayerById(UUID id);

    void createPlayer(PlayerDto playerDto, Progress progress);

}
