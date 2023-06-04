package ru.spellsofenglish.player.service.setting;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spellsofenglish.player.entity.Language;
import ru.spellsofenglish.player.entity.Settings;
import ru.spellsofenglish.player.repository.SettingsRepository;

import java.util.UUID;

@Service
public class SettingServiceImpl implements SettingService {
    private final SettingsRepository settingsRepository;

    @Autowired
    public SettingServiceImpl(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    @Override
    public Settings playerSetting(UUID playerId) {
        return null;
    }

    @Override
    @Transactional
    public Settings defaultSetting() {
        return settingsRepository.save(
                new Settings()
                        .setGameLanguage(Language.RUS)
                        .setHasAudio(true));
    }
}
