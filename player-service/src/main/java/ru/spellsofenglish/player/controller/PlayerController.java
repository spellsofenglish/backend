package ru.spellsofenglish.player.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.spellsofenglish.player.dto.player.PlayerDto;
import ru.spellsofenglish.player.dto.progress.ProgressDto;
import ru.spellsofenglish.player.entity.Player;
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
    public PlayerDto getPlayerById(@PathVariable ("id") UUID id) {
        return playerService.getPlayer(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void updatePlayer(@PathVariable ("id")  UUID id,
                              @RequestBody @Valid PlayerDto playerDto) {
        playerService.updatePlayer(id, playerDto);
    }


    @PostMapping
    public Player createPlayer(@RequestBody @Valid PlayerDto playerDto) {
        return playerService.createPlayer(playerDto, progressService.createProgress());
    }

    @GetMapping("{id}/progress")
    @ResponseStatus(HttpStatus.OK)
    public ProgressDto getPlayerProgress(@PathVariable ("id")  UUID id){
        return progressService.getPlayerProgress(playerService.findPlayerById(id));
    }

    @PatchMapping("{id}/progress")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProgress(@PathVariable ("id")  UUID id,
                               @RequestBody @Valid ProgressDto progressDto) {
        progressService.updateProgress(progressDto, playerService.findPlayerById(id).getProgress());


    }
}
