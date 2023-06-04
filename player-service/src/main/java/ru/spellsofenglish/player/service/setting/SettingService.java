package ru.spellsofenglish.player.service.setting;

import org.springframework.stereotype.Service;
import ru.spellsofenglish.player.entity.Settings;

import java.util.UUID;
@Service
public interface SettingService {
    Settings playerSetting(UUID playerId);
    Settings defaultSetting();
}
