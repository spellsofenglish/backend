package ru.spellsofenglish.player.mapper;

import org.springframework.stereotype.Service;
import ru.spellsofenglish.player.dto.progress.ProgressDto;
import ru.spellsofenglish.player.entity.Progress;

import java.util.function.Function;
@Service
public class ProgressMapperDto implements Function<Progress, ProgressDto> {

    @Override
    public ProgressDto apply(Progress progress) {
        return new ProgressDto(
                progress.getProgress(),
                progress.getGameLevel(),
                progress.getTotalPoints()
        );
    }
}
