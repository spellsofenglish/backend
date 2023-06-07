package ru.spellsofenglish.player.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spellsofenglish.player.dto.player.DataPlayerDto;
import ru.spellsofenglish.player.dto.player.PlayerDto;
import ru.spellsofenglish.player.dto.progress.ProgressDto;
import ru.spellsofenglish.player.dto.setting.SettingDto;
import ru.spellsofenglish.player.entity.Language;
import ru.spellsofenglish.player.service.PlayerService;
import ru.spellsofenglish.player.service.ProgressService;
import ru.spellsofenglish.player.service.SettingService;

@RestController
@RequestMapping("/api/v1/players")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;
    private final SettingService settingService;
    private final ProgressService progressService;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void savePlayer(@RequestBody @Valid PlayerDto dataPlayerDto){
        var settings = settingService.updateSettings(new SettingDto(Language.RUS, true));
        var progress = progressService.updateProgress(new ProgressDto(0L, 0L));
        playerService.updatePlayer(dataPlayerDto,settings,progress);
    }
    @GetMapping("/{username}")
    public ResponseEntity<DataPlayerDto> getPlayerByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(playerService.getPlayer(username));
    }

    @GetMapping("/{username}/settings")
    public ResponseEntity<SettingDto> getSettingPlayer(@PathVariable String username) {
        return ResponseEntity.ok().body(settingService.getPlayerSetting(username));
    }
    @GetMapping("/{username}/progress")
    public ResponseEntity<ProgressDto> getProgressPlayer(@PathVariable String username) {
        return ResponseEntity.ok().body(progressService.getPlayerProgress(username));
    }
}
