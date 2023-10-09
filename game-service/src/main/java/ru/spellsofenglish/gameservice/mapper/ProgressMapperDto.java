package ru.spellsofenglish.gameservice.mapper;

import org.springframework.stereotype.Service;
import ru.spellsofenglish.gameservice.dto.ProgressDto;
import ru.spellsofenglish.gameservice.models.Progress;

import java.util.function.Function;

@Service
public class ProgressMapperDto implements Function<Progress, ProgressDto> {

    @Override
    public ProgressDto apply(Progress progress) {
        return new ProgressDto(
                progress.getGameLevel(),
                progress.getTotalPoints()
        );
    }
}
