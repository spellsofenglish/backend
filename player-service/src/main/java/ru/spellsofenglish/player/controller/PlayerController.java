package ru.spellsofenglish.player.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.spellsofenglish.player.dto.player.PlayerDto;
import ru.spellsofenglish.player.dto.progress.ProgressDto;
import ru.spellsofenglish.player.exception.InvalidDataException;
import ru.spellsofenglish.player.service.PlayerService;
import ru.spellsofenglish.player.service.ProgressService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/players")
public class PlayerController {
    private final PlayerService playerService;
    private final ProgressService progressService;

    @Autowired
    public PlayerController(PlayerService playerService, ProgressService progressService) {
        this.playerService = playerService;
        this.progressService = progressService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlayerDto getPlayer(@PathVariable ("id") UUID id) throws InvalidDataException {
        return playerService.getPlayer(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void updatePlayer(@PathVariable ("id")  UUID id,
                              @RequestBody @Valid PlayerDto playerDto) throws InvalidDataException {
        playerService.updatePlayer(id, playerDto);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createPlayer(@RequestBody @Valid PlayerDto playerDto) throws InvalidDataException {
        playerService.createPlayer(playerDto, progressService.createProgress());
    }

    @GetMapping("{id}/progress")
    @ResponseStatus(HttpStatus.OK)
    public ProgressDto getPlayerProgress(@PathVariable ("id")  UUID id) throws InvalidDataException {
        return progressService.getPlayerProgress(playerService.findPlayerById(id));
    }

    @PatchMapping("{id}/progress")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProgress(@PathVariable ("id")  UUID id,
                               @RequestBody @Valid ProgressDto progressDto) throws InvalidDataException {
        progressService.updateProgress(progressDto, playerService.findPlayerById(id).getProgress());


    }
}
