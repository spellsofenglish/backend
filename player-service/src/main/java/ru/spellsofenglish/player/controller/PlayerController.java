package ru.spellsofenglish.player.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spellsofenglish.player.dto.PlayerDto;
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
    public ResponseEntity <PlayerDto> getPlayerById(@PathVariable String username){
        return ResponseEntity.ok().body(playerService.getPlayer(username));
    }
}
