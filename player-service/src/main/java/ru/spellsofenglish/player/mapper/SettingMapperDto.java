package ru.spellsofenglish.player.mapper;

import org.springframework.stereotype.Service;
import ru.spellsofenglish.player.dto.setting.SettingDto;
import ru.spellsofenglish.player.entity.Settings;

import java.util.function.Function;
@Service
public class SettingMapperDto implements Function<Settings, SettingDto> {
    @Override
    public SettingDto apply(Settings settings) {
        return new SettingDto(
                settings.getGameLanguage(),
                settings.getHasAudio()
        );
    }
}
