package ru.spellsofenglish.player.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spellsofenglish.player.dto.setting.SettingDto;
import ru.spellsofenglish.player.entity.Settings;
import ru.spellsofenglish.player.mapper.SettingMapperDto;
import ru.spellsofenglish.player.repository.SettingsRepository;
import ru.spellsofenglish.player.service.SettingService;

@Service
public class SettingServiceImpl implements SettingService {
    private final SettingsRepository settingsRepository;
    private final SettingMapperDto settingMapperDto;

    @Autowired
    public SettingServiceImpl(SettingsRepository settingsRepository, SettingMapperDto settingMapperDto) {
        this.settingsRepository = settingsRepository;
        this.settingMapperDto = settingMapperDto;
    }

    @Override
    public SettingDto getPlayerSetting(String username) {
        return settingsRepository.findByUsername(username)
                .map(settingMapperDto)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    public Settings updateSettings(SettingDto settingDto) {
        var setting = new Settings()
                .setGameLanguage(settingDto.game_language())
                .setHasAudio(settingDto.hasAudio());
        return settingsRepository.save(setting);
    }
}
