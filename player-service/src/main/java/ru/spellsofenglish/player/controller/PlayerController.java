package ru.spellsofenglish.player.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import ru.spellsofenglish.player.dto.player.PlayerDto;
import ru.spellsofenglish.player.dto.progress.ProgressDto;
import ru.spellsofenglish.player.exception.InvalidDataException;
import ru.spellsofenglish.player.service.PlayerService;
import ru.spellsofenglish.player.service.ProgressService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/players")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;
    private final ProgressService progressService;

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable UUID id) throws InvalidDataException {
        return ResponseEntity.ok(playerService.getPlayer(id));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void updatePlayer(@PathVariable UUID id,
                            @RequestBody @Valid PlayerDto playerDto) throws InvalidDataException {
        playerService.updatePlayer(id,playerDto);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createPlayer(@RequestBody @Valid PlayerDto playerDto) throws InvalidDataException {
        playerService.createPlayer(playerDto, progressService.createProgress());
    }

    @GetMapping("{id}/progress")
    public ResponseEntity<ProgressDto> getPlayerProgress(@PathVariable UUID id) throws InvalidDataException {
        return ResponseEntity.ok(progressService.getPlayerProgress(
                playerService.findPlayerById(id)));
    }

    @PatchMapping("{id}/progress")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProgress(@PathVariable UUID id,
                               @RequestBody @Valid ProgressDto progressDto) throws InvalidDataException {
        progressService.updateProgress(progressDto, playerService.findPlayerById(id).getProgress());


    }
}
