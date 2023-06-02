package ru.spellsofenglish.player.service.player;

import org.springframework.stereotype.Service;
import ru.spellsofenglish.player.dto.PlayerDto;
@Service
public interface PlayerService {
    PlayerDto getPlayer(String username);
}
