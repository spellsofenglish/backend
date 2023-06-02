package ru.spellsofenglish.player.service.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spellsofenglish.player.dto.PlayerDto;
import ru.spellsofenglish.player.mapper.PlayerMapperDto;
import ru.spellsofenglish.player.repository.PlayerRepository;


@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerMapperDto playerMapperDto;
    private final PlayerRepository playerRepository;
    @Autowired
    public PlayerServiceImpl(PlayerMapperDto playerMapperDto, PlayerRepository playerRepository) {
        this.playerMapperDto = playerMapperDto;
        this.playerRepository = playerRepository;
    }

    @Override
    public PlayerDto getPlayer(String username) {
        return playerRepository.findByUsername(username)
                .map(playerMapperDto)
                .orElseThrow(() -> new IllegalArgumentException("User not fount"));
    }
}
