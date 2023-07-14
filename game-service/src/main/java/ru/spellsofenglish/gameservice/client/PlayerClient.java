package ru.spellsofenglish.gameservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.spellsofenglish.gameservice.dto.ProgressDto;
import ru.spellsofenglish.gameservice.models.Player;

import java.util.Optional;
import java.util.UUID;

@FeignClient(name = "player-service")
public interface PlayerClient {
    @GetMapping("/api/v1/players/{id}")
    Optional<Player> getPlayerById(@PathVariable("id") UUID id);
    @RequestMapping(method = RequestMethod.PATCH, value = "/api/v1/players/{id}/progress", consumes = "application/json")
    void updatePlayer(@PathVariable("id") UUID id, ProgressDto progressDto);
}
