package ru.spellsofenglish.player.service;

import org.springframework.stereotype.Service;
import ru.spellsofenglish.player.dto.player.PlayerDto;
import ru.spellsofenglish.player.entity.Player;
import ru.spellsofenglish.player.entity.Progress;
import ru.spellsofenglish.player.exception.InvalidDataException;

import java.util.UUID;

@Service
public interface PlayerService {
    PlayerDto getPlayer(UUID id) throws InvalidDataException;

    Player findPlayerById(UUID id) throws InvalidDataException;

    void createPlayer(PlayerDto playerDto, Progress progress) throws InvalidDataException;
    void updatePlayer(UUID id, PlayerDto playerDto) throws InvalidDataException;

}
