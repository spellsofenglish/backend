package ru.spellsofenglish.player.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spellsofenglish.player.dto.player.PlayerDto;
import ru.spellsofenglish.player.entity.Player;

import java.util.function.Function;
@Service
@RequiredArgsConstructor
public class PlayerMapperDto implements Function<Player, PlayerDto>  {
    private final ProgressMapperDto progressMapperDto;
    @Override
    public PlayerDto apply(Player player) {
        return new PlayerDto(
                player.getId(),
                player.getUsername(),
                progressMapperDto.apply(player.getProgress())
        );
    }
}
