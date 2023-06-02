package ru.spellsofenglish.player.mapper;
import org.springframework.stereotype.Service;
import ru.spellsofenglish.player.dto.PlayerDto;
import ru.spellsofenglish.player.entity.Player;

import java.util.function.Function;
@Service
public class PlayerMapperDto implements Function<Player, PlayerDto>  {
    @Override
    public PlayerDto apply(Player player) {
        return new PlayerDto(
                player.getUsername(),
                player.getPoints()
        );
    }
}
