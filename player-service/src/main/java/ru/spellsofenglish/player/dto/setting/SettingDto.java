package ru.spellsofenglish.player.dto.setting;

import ru.spellsofenglish.player.entity.Language;

public record SettingDto(
        Language game_language,
        Boolean hasAudio
) {
}
