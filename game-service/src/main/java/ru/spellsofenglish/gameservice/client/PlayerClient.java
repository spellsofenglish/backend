package ru.spellsofenglish.gameservice.client;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.spellsofenglish.gameservice.dto.ProgressDto;
import ru.spellsofenglish.gameservice.models.Player;

import java.util.Optional;
import java.util.UUID;

@FeignClient(name = "player-service")
public interface PlayerClient {
    @GetMapping("/api/v1/players/{id}")
    Optional<Player> getPlayerById(@PathVariable("id") UUID id);

    @PutMapping("/api/v1/players/{id}/progress")
    void updatePlayer(@PathVariable("id") UUID id, @Valid ProgressDto progressDto);

    @PutMapping("/api/v1/players/{id}/allowMove")
    void allowOrDenyMovePlayer(@PathVariable ("id") UUID id, @RequestBody boolean allowMove);
}
