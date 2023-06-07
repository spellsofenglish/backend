package ru.spellsofenglish.player.service;

import org.springframework.stereotype.Service;
import ru.spellsofenglish.player.dto.setting.SettingDto;
import ru.spellsofenglish.player.entity.Settings;

@Service
public interface SettingService {
    SettingDto getPlayerSetting(String username);
    Settings updateSettings(SettingDto settingDto);
}
