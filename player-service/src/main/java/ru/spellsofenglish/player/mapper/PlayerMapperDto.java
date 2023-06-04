package ru.spellsofenglish.player.mapper;
import org.springframework.stereotype.Service;
import ru.spellsofenglish.player.dto.player.DataPlayerDto;
import ru.spellsofenglish.player.entity.Player;

import java.util.function.Function;
@Service
public class PlayerMapperDto implements Function<Player, DataPlayerDto>  {
    @Override
    public DataPlayerDto apply(Player player) {
        return new DataPlayerDto(
                player.getUsername(),
                player.getPoints(),
                player.getProgress().getProgress(),
                player.getProgress().getGameLevel()
        );
    }
}
