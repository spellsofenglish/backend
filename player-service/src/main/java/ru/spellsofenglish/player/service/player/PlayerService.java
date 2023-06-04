package ru.spellsofenglish.player.service.player;

import org.springframework.stereotype.Service;
import ru.spellsofenglish.player.dto.player.DataPlayerDto;
import ru.spellsofenglish.player.dto.player.PlayerDto;

@Service
public interface PlayerService {
    DataPlayerDto getPlayer(String username);
    void savePlayer(PlayerDto playerDto);
}
