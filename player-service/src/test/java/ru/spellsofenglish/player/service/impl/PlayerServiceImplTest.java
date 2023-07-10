package ru.spellsofenglish.player.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.spellsofenglish.player.dto.player.PlayerDto;
import ru.spellsofenglish.player.entity.Player;
import ru.spellsofenglish.player.entity.Progress;
import ru.spellsofenglish.player.repository.PlayerRepository;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {
    @InjectMocks
    private PlayerServiceImpl playerService;
    @Mock
    private PlayerRepository playerRepository;
    @Test
    void findPlayerById() {

    }

    @Test
    void createPlayer() {
    }

    @Test
    void updatePlayer() {
    }
}