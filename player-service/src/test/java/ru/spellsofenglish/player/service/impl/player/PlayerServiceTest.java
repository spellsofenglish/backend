package ru.spellsofenglish.player.service.impl.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.spellsofenglish.player.dto.player.PlayerDto;
import ru.spellsofenglish.player.entity.Player;
import ru.spellsofenglish.player.entity.Progress;
import ru.spellsofenglish.player.exception.InvalidDataException;
import ru.spellsofenglish.player.repository.PlayerRepository;
import ru.spellsofenglish.player.service.PlayerService;
import ru.spellsofenglish.player.service.impl.PlayerServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayerServiceTest {
    private PlayerService playerService;

    @Mock
    private PlayerRepository playerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        playerService = new PlayerServiceImpl(playerRepository);
    }

    @Test
    void findPlayerById_WhenPlayerExists_ShouldReturnPlayer() {
        UUID playerId = UUID.randomUUID();
        Player player = new Player();
        player.setId(playerId);
        when(playerRepository.findPlayerById(playerId)).thenReturn(Optional.of(player));

        Player result = playerService.findPlayerById(playerId);

        assertEquals(playerId, result.getId());
        verify(playerRepository, times(1)).findPlayerById(playerId);
    }

    @Test
    void findPlayerById_WhenPlayerDoesNotExist_ShouldThrowException() {
        UUID playerId = UUID.randomUUID();
        when(playerRepository.findPlayerById(playerId)).thenReturn(Optional.empty());

        assertThrows(InvalidDataException.class, () -> playerService.findPlayerById(playerId));

        verify(playerRepository, times(1)).findPlayerById(playerId);
    }

    @Test
    void createPlayer_WhenUsernameIsAvailable_ShouldCreatePlayer() {
        UUID playerId = UUID.randomUUID();
        PlayerDto playerDto = new PlayerDto(playerId, "username");
        Progress progress = new Progress();

        when(playerRepository.existsByUsername(playerDto.username())).thenReturn(false);
        when(playerRepository.save(any(Player.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Return the saved player

        Player result = playerService.createPlayer(playerDto, progress);

        assertEquals(playerDto.username(), result.getUsername());
        assertEquals(progress, result.getProgress());
        assertTrue(result.isAllowMove());
        verify(playerRepository, times(1)).existsByUsername(playerDto.username());
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void createPlayer_WhenUsernameIsTaken_ShouldThrowException() {
        UUID playerId = UUID.randomUUID();
        PlayerDto playerDto = new PlayerDto(playerId, "username");

        when(playerRepository.existsByUsername(playerDto.username())).thenReturn(true);

        assertThrows(InvalidDataException.class, () -> playerService.createPlayer(playerDto, new Progress()));

        verify(playerRepository, times(1)).existsByUsername(playerDto.username());
        verify(playerRepository, never()).save(any(Player.class));
    }

}
