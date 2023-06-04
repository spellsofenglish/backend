package ru.spellsofenglish.player.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spellsofenglish.player.dto.player.DataPlayerDto;
import ru.spellsofenglish.player.dto.player.PlayerDto;
import ru.spellsofenglish.player.service.player.PlayerService;

@RestController
@RequestMapping("/api/v1/player")
public class PlayerController {
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<DataPlayerDto> getPlayerByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(playerService.getPlayer(username));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void savePlayer(@RequestBody @Valid PlayerDto dataPlayerDto){
        playerService.savePlayer(dataPlayerDto);
    }
}
